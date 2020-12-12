package com.project.securedoor.Controller;

import com.project.securedoor.Model.ConfirmationToken;
import com.project.securedoor.Model.UserModel;
import com.project.securedoor.Model.UserRequestModel;
import com.project.securedoor.Repository.ConfirmationTokenRepository;
import com.project.securedoor.Repository.UserRepository;
import com.project.securedoor.Service.EmailSenderService;
import com.project.securedoor.Service.UserService;
import org.apache.catalina.Store;
import org.hibernate.loader.plan.spi.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> verifyValidNewUser(@RequestBody UserRequestModel userRequestModel) throws Exception {
        userService.loadUserByUsernameForCheck(userRequestModel.getUsername());
        if(userService.isNewUser) {
            UserModel user = userService.save(userRequestModel);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getUsername());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("champmend@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8040/confirm-account?token="+confirmationToken.getConfirmationToken());
            try{
                emailSenderService.sendMail(mailMessage);
            }catch(Exception e){
                e.getStackTrace();
                System.out.println(e.getStackTrace());
            }
        }
        return ResponseEntity.ok("Mail sent...");
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = (ConfirmationToken) confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            UserModel user = userRepository.findByUsername(token.getUser().getUserName());
            user.setIsVerified(true);
            userRepository.save(user);
            return ResponseEntity.ok("Account Verified");
        }
        else
        {
            return ResponseEntity.ok("The link is invalid or broken!");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam("username")String username, @RequestParam("password")String password) {
        if(userService.isCorrectUser(username,password)){

            return ResponseEntity.ok("Valid credentials");
        }
        else {
            return ResponseEntity.ok("Invalid username or password");
        }
    }
}
