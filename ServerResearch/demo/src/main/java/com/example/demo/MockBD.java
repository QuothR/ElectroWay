package com.example.demo;

import com.example.demo.user.User;

import java.util.ArrayList;
import java.util.List;

public class MockBD {
    private static MockBD instance = null;
    private List<User> users = new ArrayList<>();

    private MockBD(List<User> users) {
        this.users = users;
    }

    public static MockBD getInstance() {
        if (instance == null) {
            instance = new MockBD(new ArrayList<>(List.of(
                    new User(
                            "alina@gmail.com",
                            "parola123",
                            "Eu"
                    ),
                    new User(
                            "marcel@yahoo.com",
                            "blabla",
                            "Marcel"
                    ),
                    new User(
                            "andrei@info.uaic.ro",
                            "pass",
                            "Andrei"
                    )
            )));
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
