package com.example.demo.user;

import com.example.demo.MockBD;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    private final MockBD bd = MockBD.getInstance();

    @GetMapping
    public List<User> getUsers() {
        return bd.getUsers();
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestBody User newUser) {
        for (User user : bd.getUsers()) {
            if (user.getEmail().equals(newUser.getEmail())) {
                return new ResponseEntity<>("Email already used.", HttpStatus.CONFLICT);
            }
        }

        bd.addUser(newUser);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestBody User user) {
        for (User currentUser : bd.getUsers()) {
            if (currentUser.getEmail().equals(user.getEmail())) {
                if (!currentUser.getPassword().equals(user.getPassword())) {
                    return new ResponseEntity<>("Incorrect password.", HttpStatus.FORBIDDEN);
                } else {
                    return new ResponseEntity<>("Success", HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("User doesn't exist.", HttpStatus.BAD_REQUEST);
    }
}
