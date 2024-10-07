package coms309.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long AdminId;

    @Column(name = "name", nullable = false)
    private String AdminName;

    @Column(name="UserProfile", nullable = false)
    private UserProfile userProfile;
}
