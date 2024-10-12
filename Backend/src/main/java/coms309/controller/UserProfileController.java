package coms309.controller;

import coms309.dto.UserDTO;
import coms309.dto.UserSignupDTO;
import coms309.entity.Admin;
import coms309.entity.Employee;
import coms309.entity.Employer;
import coms309.entity.UserProfile;
import coms309.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @PutMapping("/password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserDTO forgotUser) {
        Optional<UserProfile> user = userProfileRepository.findByEmail(forgotUser.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("No user exist");
        }
        user.get().setPassword(forgotUser.getPassword());
        userProfileRepository.save(user.get());
        return ResponseEntity.ok("Successfully change the password");
    }

    /**
     * Get all user profiles.
     * @return List of UserProfile
     */
    @GetMapping("/all")
    public List<UserProfile> getAllUserProfiles() {
        logger.info("Fetching all user profiles");
        return userProfileRepository.findAll();
    }

    /**
     * Get a user profile by its ID.
     * @param id ID of the user profile
     * @return ResponseEntity with UserProfile or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        logger.info("Fetching user profile with ID: {}", id);

        // Validate ID input
        if (id <= 0) {
            logger.warn("Invalid ID supplied: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<UserProfile> userProfile = userProfileRepository.findById(id);

        return userProfile.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("User profile not found for ID: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }



    @GetMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserDTO loginUser) {
        Optional<UserProfile> existUser = userProfileRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
        if (existUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Login failed");
        }
        UserProfile userProfile = userProfileRepository.save(existUser.get());
        return ResponseEntity.ok("Login successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody UserSignupDTO signUpUserProfile){
        //change it to check seperately the email pass and username

        Optional<UserProfile> existingUsername = userProfileRepository.findByUsername(signUpUserProfile.getUsername());
        Optional<UserProfile> existingEmail = userProfileRepository.findByEmail( signUpUserProfile.getEmail());
        if(existingUsername.isPresent()){
            return ResponseEntity.badRequest().body("Sign up fail");
        }
        if(existingEmail.isPresent()){
            return ResponseEntity.badRequest().body("Sign up fail");
        }
        UserProfile userProfile = userProfileRepository.save(new UserProfile(signUpUserProfile.getUsername(), signUpUserProfile.getEmail(), signUpUserProfile.getPassword()));
        return ResponseEntity.ok("Sign up successfully");
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfileDetails) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            userProfile.setUsername(userProfileDetails.getUsername());
            userProfile.setPassword(userProfileDetails.getPassword());
            userProfile.setUserType(userProfileDetails.getUserType());
            userProfile.setEmail(userProfileDetails.getEmail());
            userProfile.setJobTitle(userProfileDetails.getJobTitle());
            userProfile.setDepartment(userProfileDetails.getDepartment());
            userProfile.setDateOfHire(userProfileDetails.getDateOfHire());

            UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
            return ResponseEntity.ok(updatedUserProfile);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    public void createEmployeeRecord(UserProfile userProfile, UserProfile userProfileDetails) {
        // Insert record into employee table
        Employee employee = new Employee();
        employee.setUserProfile(userProfile);

        // Save employee entity (assuming you have an EmployeeRepository)
    }
    public void createEmployerRecord(UserProfile userProfile, UserProfile userProfileDetails){
        Employer er = new Employer();
        er.setUserProfile(userProfile);
    }
    public void createAdminRecord(UserProfile userProfile, UserProfile userProfileDetails){
        Admin admin = new Admin();
        admin.setUserProfile(userProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        if (userProfileRepository.existsById(id)) {
            userProfileRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
