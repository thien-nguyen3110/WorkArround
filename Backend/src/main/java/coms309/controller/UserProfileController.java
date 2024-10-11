package coms309.controller;

import coms309.dto.UserDTO;
import coms309.entity.UserProfile;
import coms309.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

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

    @GetMapping("/all")
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        return userProfile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserDTO loginUser) {
        Optional<UserProfile> existUser = userProfileRepository.findByUserNameAndPassword(loginUser.getUsername(), loginUser.getPassword());
        if (existUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Login failed");
        }
        return ResponseEntity.ok("Login successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody UserDTO signUpUserProfile){
        Optional<UserProfile> existingAccount = userProfileRepository.findByUserNameAndEmailAndPassword(signUpUserProfile.getUsername(), signUpUserProfile.getEmail(),signUpUserProfile.getPassword());

        if(existingAccount.isPresent()){
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
            userProfile.setUserName(userProfileDetails.getUserName());
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
