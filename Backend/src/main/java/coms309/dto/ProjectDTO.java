package coms309.dto;


import coms309.entity.Priority;
import coms309.entity.UserProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Data
public class ProjectDTO {


    private String Description;

    private String projectName;

    private String status;

    private Date dueDate;

    private Priority priority;


    @NotEmpty(message = "Employers list cannot be empty")
    private List<@NotBlank(message = "Employer username cannot be blank") String> employerUsernames;

}
