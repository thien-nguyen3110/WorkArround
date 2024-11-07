package coms309.service;

import coms309.dto.ProjectDTO;
import coms309.entity.Employer;
import coms309.entity.Projects;
import coms309.entity.UserType;
import coms309.repository.ProjectRepository;
import coms309.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<Projects> getAllProjects() {
        return projectRepository.findAll();
    }

    public ResponseEntity<Projects> getProjectById(Long id) {
        Optional<Projects> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<String> createProject(ProjectDTO projectDTO) {
        // Check if a project with the same name already exists
        Optional<Projects> existingProject = projectRepository.findByProjectName(projectDTO.getProjectName());
        if (existingProject.isPresent()) {
            return ResponseEntity.badRequest().body("Project with the same name already exists");
        }


        if (projectDTO.getPriority() == null) {
            return ResponseEntity.badRequest().body("Priority level is required");
        }


        Projects project = new Projects();
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        project.setDueDate(projectDTO.getDueDate());
        project.setStatus(projectDTO.getStatus());
        project.setPriority(projectDTO.getPriority());


        List<String> employerUsernames = projectDTO.getEmployerUsernames();

        if (employerUsernames != null && !employerUsernames.isEmpty()) {
            // Fetch employers by usernames and ensure they are EMPLOYER type
            List<Employer> employers = userProfileRepository.findAllEmployersByUsernameInAndUserType(
                    employerUsernames,
                    UserType.EMPLOYER
            );


            if (employers.size() != employerUsernames.size()) {

                Set<String> foundUsernames = employers.stream()
                        .map(emp -> emp.getUserProfile().getUsername())
                        .collect(Collectors.toSet());
                List<String> notFoundUsernames = employerUsernames.stream()
                        .filter(username -> !foundUsernames.contains(username))
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(
                        "Employers not found or not of type EMPLOYER: " + String.join(", ", notFoundUsernames)
                );
            }

            // Assign employers to the project
            project.setEmployers(employers.stream().collect(Collectors.toSet()));
            employers.forEach(employer -> employer.getProjects().add(project));
        }


        projectRepository.save(project);



        return ResponseEntity.ok("Project created successfully");
    }


    @Transactional
    public ResponseEntity<String> updateProject(Long id, ProjectDTO projectDTO) {
        Optional<Projects> existingProjectOpt = projectRepository.findById(id);
        if (existingProjectOpt.isPresent()) {
            Projects existingProject = existingProjectOpt.get();
            existingProject.setDescription(projectDTO.getDescription());
            existingProject.setDueDate(projectDTO.getDueDate());
            existingProject.setProjectName(projectDTO.getProjectName());
            existingProject.setStatus(projectDTO.getStatus());


            if (projectDTO.getPriority() != null) {
                existingProject.setPriority(projectDTO.getPriority());
            }

            List<String> employerUsernames = projectDTO.getEmployerUsernames();

            if (employerUsernames != null) {
                existingProject.getEmployers().forEach(employer -> employer.getProjects().remove(existingProject));
                existingProject.getEmployers().clear();

                if (!employerUsernames.isEmpty()) {
                    List<Employer> newEmployers = userProfileRepository.findAllEmployersByUsernameInAndUserType(
                            employerUsernames,
                            UserType.EMPLOYER
                    );


                    if (newEmployers.size() != employerUsernames.size()) {
                        Set<String> foundUsernames = newEmployers.stream()
                                .map(emp -> emp.getUserProfile().getUsername())
                                .collect(Collectors.toSet());
                        List<String> notFoundUsernames = employerUsernames.stream()
                                .filter(username -> !foundUsernames.contains(username))
                                .collect(Collectors.toList());
                        return ResponseEntity.badRequest().body(
                                "Employers not found or not of type EMPLOYER: " + String.join(", ", notFoundUsernames)
                        );
                    }

                    // Assign new employers to the project
                    existingProject.setEmployers(newEmployers.stream().collect(Collectors.toSet()));
                    newEmployers.forEach(employer -> employer.getProjects().add(existingProject));
                }
            }

            projectRepository.save(existingProject);
            return ResponseEntity.ok("Project updated successfully");
        }
        return ResponseEntity.notFound().build();
    }






}