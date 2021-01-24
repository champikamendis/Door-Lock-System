package com.project.securedoor.Controller;

import com.project.securedoor.Configuration.JwtTokenUtil;
import com.project.securedoor.Configuration.WebSecurityConfig;
import com.project.securedoor.Model.*;
import com.project.securedoor.Repository.ConfirmationTokenRepository;
import com.project.securedoor.Repository.UserRepository;
import com.project.securedoor.Service.EmailSenderService;
import com.project.securedoor.Service.OTPService;
import com.project.securedoor.Service.UserService;
import org.apache.catalina.Store;
import org.hibernate.loader.plan.spi.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private WebSecurityConfig webSecurityConfig;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserRequestModel userRequestModel) throws Exception {
        userService.loadUserByUsernameForCheck(userRequestModel.getUsername());
        if (userService.isNewUser) {
            UserModel user = userService.save(userRequestModel);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getUsername());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("champmend@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8040/register/confirm-account?token=" + confirmationToken.getConfirmationToken());
            try {
                emailSenderService.sendMail(mailMessage);
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(e.getStackTrace());
            }
        }
        return ResponseEntity.ok("Verification email sent. You have to confirm it's you");
    }

    @RequestMapping(value = "/register/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = (ConfirmationToken) confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            UserModel user = userRepository.findByUsername(token.getUser().getUserName());
            user.setIsVerified(true);
            userRepository.save(user);
            return ResponseEntity.ok("Verified");
        } else {
            return ResponseEntity.ok("Not Verified");
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        if(token!=null){
            return ResponseEntity.ok(new JwtResponse(token));
        }else{
            return ResponseEntity.ok("Not Okay");
        }
    }

        @RequestMapping(value = "/authenticate/generateOtp", method = RequestMethod.GET)
        public ResponseEntity<?> generateOTP() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            int otp = otpService.generateOTP(username);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(username);
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("champmend@gmail.com");
            mailMessage.setText("Here is the OTP " + otp);
            try {
                emailSenderService.sendMail(mailMessage);
                ResponseEntity.ok("Mail sent");
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(e.getStackTrace());
                return ResponseEntity.ok("Not mail sent");
            }
            return ResponseEntity.ok("See your Emails");

        }

        @RequestMapping(value = "/authenticate/generateOtp/validateOtp", method = RequestMethod.POST)
        public ResponseEntity<?> validateOtp(@RequestParam("otpnum") int otpnum, HttpServletResponse response) {

            final String SUCCESS = "Entered Otp is valid";
            final String FAIL = "Entered Otp is NOT valid. Please Retry!";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            //Validate the Otp
            if (otpnum >= 0) {

                int serverOtp = otpService.getOtp(username);
                if (serverOtp > 0) {
                    if (otpnum == serverOtp) {
                        otpService.clearOTP(username);

                        return ResponseEntity.ok(SUCCESS);
                    } else {
                        return ResponseEntity.ok(FAIL);
                    }
                } else {
                    return ResponseEntity.ok(FAIL);
                }
            } else {
                return ResponseEntity.ok(FAIL);
            }
        }



        @RequestMapping(value = "/authenticate/generateOtp/validateOtp/access", method = RequestMethod.GET)
        public ResponseEntity<?> accessGrant() throws Exception {
            return ResponseEntity.ok("Granted the access successful");
        }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

