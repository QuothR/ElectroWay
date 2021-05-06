package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.dtos.UserDto;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.models.VerificationToken;
import com.example.electrowayfinal.service.UserService;
import com.example.electrowayfinal.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    //TODO Automated testing
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    @Autowired //dependency injection, userService is automatically instantiated
    public UserController(UserService userService, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }

    @GetMapping("/")
    public String home() {
        return ("<h1>Welcome");
    }

    @GetMapping("/user")
    public Optional<User> getCurrentUser(HttpServletRequest httpServletRequest) {
        return userService.getCurrentUser(httpServletRequest);
    }

    @GetMapping("/users")
    public List<User> users() {
        return this.userService.getUsers();
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public Optional<UserDto> registerNewUser(@Valid @RequestBody UserDto userDto) throws DataIntegrityViolationException {

        userService.registerNewUserAccount(userDto);
        return userService.getOptionalUserDto(userDto.getEmailAddress());
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
    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody User modifiedUser, HttpServletRequest httpServletRequest) throws Exception {
        userService.updateUser(modifiedUser, httpServletRequest);
    }

    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "token invalid.");
        } else {
            User user = verificationToken.getUser();

            if (!user.isEnabled()) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                if (verificationToken.getExpiryDate().before(currentTimestamp)) {
                    model.addAttribute("message", "token expired.");
                } else {
                    userService.enableUser(user.getId());
                    model.addAttribute("message", "successfully activated");
                }
            } else {
                model.addAttribute("message", "already activated");
            }
        }
        return "activation";
    }

    @GetMapping("/authenticated")
    public String authenticated() {
        return ("<h1>Success!");
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        model.addAttribute("token", token);
        return ("reset password form");
    }
}
