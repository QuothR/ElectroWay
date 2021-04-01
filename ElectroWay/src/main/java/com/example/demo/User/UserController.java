package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("users")
public class UserController {
    //TODO Automated testing

    private final UserService userService;

    @GetMapping("/")
    public String home(){
        return ("<h1>Welcome");
    }

    @GetMapping("/user")
    public String user(){
        return ("<h1>Welcome User");
    }


    @Autowired //dependency injection, userService is automatically instantiated
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/register")
    public Optional<User> registerNewUser(@RequestBody User user){
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
            @RequestParam(required = false) String first_name,
            @RequestParam(required = false) String last_name,
            @RequestParam(required = false) String email){
        userService.updateUser(id, first_name, last_name, email);
    }

}
