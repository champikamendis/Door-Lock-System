package com.project.securedoor.Controller;

import com.project.securedoor.Model.ConfirmationToken;
import com.project.securedoor.Model.UserModel;
import com.project.securedoor.Model.UserRequestModel;
import com.project.securedoor.Repository.ConfirmationTokenRepository;
import com.project.securedoor.Repository.UserRepository;
import com.project.securedoor.Service.EmailSenderService;
import com.project.securedoor.Service.UserService;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController{
	
	@Bean
    public UserService userService() {
        return new UserService();
    }
    @Autowired
    private UserService userService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSenderService emailSenderService;
    
    
    
    
//    private static final String PATH="{/error}";
//    
////    @RequestMapping(value = PATH, method = RequestMethod.GET)
////    public String defultErrorMassege() {
////    	return "Request not found..";
////    }
//    
//    @Override
//    public String getErrorPath() {
//    	return PATH;
//    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView ViewHomePage() {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("usermodel", new UserModel());
    	mav.setViewName("register");  // resources/templates/register.html
    	return mav;
    }
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
            mailMessage.setFrom("mpsampath16@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8030/confirm-account?token="+confirmationToken.getConfirmationToken());
            try{
                emailSenderService.sendMail(mailMessage);

            }catch(Exception e){
                e.getStackTrace();
                System.out.println(e.getStackTrace());
            }
        
        }
        
        return ResponseEntity.ok("Email is send....");
        
        }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = (ConfirmationToken) confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            UserModel user = userRepository.findByUsername(token.getUser().getUserName());
            user.setEnabled(true);
            userRepository.save(user);
            return("Account Verified");
        }
        else
        {
            return("The link is invalid or broken!");
        }
    }
}
