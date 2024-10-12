package coms309.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

public class EmailDTO {
    private String email;

    // Getter and Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}