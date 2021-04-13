package com.example.electrowayfinal.user;

import com.example.electrowayfinal.emailVerification.VerificationToken;
import com.example.electrowayfinal.service.UserService;
import com.example.electrowayfinal.service.VerificationTokenService;
import com.example.electrowayfinal.validation.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("users")
public class UserController {
    //TODO Automated testing

    private final UserService userService;
    private VerificationTokenService verificationTokenService;

    @GetMapping("/")
    public String home(){
        return ("<h1>Welcome");
    }

    @GetMapping("/user")
    public String user(){
        return  this.userService.getUsers().toString();
    }

    @Autowired //dependency injection, userService is automatically instantiated
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<com.example.electrowayfinal.user.User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/register")
    public Optional<com.example.electrowayfinal.user.User> registerNewUser(@RequestBody com.example.electrowayfinal.user.User user){
        userService.registerNewUserAccount(user);
        return userService.getOptionalUser(user);
    }
    /*
        @RequestMapping("login")
        @PutMapping()
        public void login(
                HttpServletRequest request,
                HttpServletResponse response,
                @RequestParam(required = true) String email,
                @RequestParam(required = true) String password
        ){
            userService.login(request,response,email,password);
        }
    */
    @DeleteMapping(path =  "{userId}")
    public void deleteUser(@PathVariable("userId") Long id){
        userService.deleteUser(id);
    }

    @PutMapping("{userId}")
    public void updateStudent(
            @PathVariable("userId") Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String emailAddress){
        userService.updateUser(id, firstName, lastName, emailAddress);
    }

    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model){
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if(verificationToken == null){
            model.addAttribute("message","token invalid.");
        }else{
            User user = verificationToken.getUser();

            if(!user.isEnabled()){
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                if(verificationToken.getExpiryDate().before(currentTimestamp)){
                    model.addAttribute("message","token expired.");
                }else{
                    userService.enableUser(user.getId());
                    model.addAttribute("message","successfully activated");
                }
            }else{
                model.addAttribute("message","already activated");
            }
        }
        return "activation";
    }

    @GetMapping("/authenticated")
    public String authenticated(){
        return ("<h1>Success!");
    }
}
