package coms309.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserSignUp {
    @NonNull
    private String password;

    @NonNull
    private String username;

    private String email;
}
