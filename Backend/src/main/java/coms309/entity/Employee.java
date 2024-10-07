package coms309.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "name", nullable = false)
        private String employeeName;

        @Column(name="UserProfile", nullable = false)
        private UserProfile userProfile;


        @GetMapping("\users")
        public String

}
