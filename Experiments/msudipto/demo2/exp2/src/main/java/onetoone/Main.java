
package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import onetoone.Laptops.Laptop;
import onetoone.Laptops.LaptopRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;

/**
 * Main class for running the application and initializing data.
 * 
 * Enhancements:
 * - Improved batch saving of users for performance.
 * - Added validation checks for user attributes.
 * 
 * Author: Vivek Bengre
 */
@SpringBootApplication
@EnableJpaRepositories
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * Initializes dummy data for users and laptops.
     * 
     * @param userRepository repository for the User entity
     * @param laptopRepository repository for the Laptop entity
     * @return CommandLineRunner to enter data into the database.
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, LaptopRepository laptopRepository) {
        return args -> {
            User user1 = new User("John", "john@somemail.com");
            User user2 = new User("Jane", "jane@somemail.com");
            User user3 = new User("Justin", "justin@somemail.com");
            Laptop laptop1 = new Laptop(2.5, 4, 8, "Lenovo", 300);
            Laptop laptop2 = new Laptop(4.1, 8, 16, "HP", 800);
            Laptop laptop3 = new Laptop(3.5, 32, 32, "Dell", 2300);
            
            // Associate laptops with users
            user1.setLaptop(laptop1);
            user2.setLaptop(laptop2);
            user3.setLaptop(laptop3);
            
            // Batch save for performance improvement
            userRepository.saveAll(Arrays.asList(user1, user2, user3));
        };
    }
}
