package coms309.dto;

import lombok.Data;

@Data
public class UserSignUp {
    private String password;

    private String username;

    private String email;
}
