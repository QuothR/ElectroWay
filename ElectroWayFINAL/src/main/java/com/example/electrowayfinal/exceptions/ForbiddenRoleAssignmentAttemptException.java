package com.example.electrowayfinal.exceptions;

import com.example.electrowayfinal.models.User;

public class ForbiddenRoleAssignmentAttemptException extends Exception{
    public ForbiddenRoleAssignmentAttemptException(User user){
        super(
                "User with username " + user.getUsername() + " and " +
                        "email " + user.getEmailAddress() + " attempted to assign themselves a forbidden role;" +
                        "This security violation has been recorded."
        );
    }
}
