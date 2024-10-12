package coms309.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "user_profiles")
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = true)
    private UserType userType;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "job_title", nullable = true)
    private String jobTitle;

    @Column(name = "department", nullable = true)
    private String department;

    @Column(name = "date_of_hire", nullable = true)
    private Date dateOfHire;


    public UserProfile(String username, String email, String password) {
        this.password = password;
        this.username = this.username;
        this.email = email;
        this.dateOfHire = new Date();
    }

    public UserProfile() {}


}
