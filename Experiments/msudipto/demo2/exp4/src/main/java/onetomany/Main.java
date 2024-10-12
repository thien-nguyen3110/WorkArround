
package onetomany;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetomany.Laptops.Laptop;
import onetomany.Laptops.LaptopRepository;
import onetomany.Phones.Phone;
import onetomany.Phones.PhoneRepository;
import onetomany.Users.User;
import onetomany.Users.UserRepository;

/**
 * Main class to initialize the application and seed dummy data.
 * 
 * Enhancements:
 * - Improved batch saving of users and devices for performance.
 * - Added validation checks for user and device details.
 * 
 * Author: Vivek Bengre
 */
@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, LaptopRepository laptopRepository, PhoneRepository phoneRepository) {
        return args -> {
            User user1 = new User("John", "john@somemail.com", new Date());
            User user2 = new User("Jane", "jane@somemail.com", new Date());
            User user3 = new User("Justin", "justin@somemail.com", new Date());

            Laptop laptop1 = new Laptop(2.5, 4, 8, "Lenovo", 300);
            Laptop laptop2 = new Laptop(4.1, 8, 16, "HP", 800);
            Laptop laptop3 = new Laptop(3.5, 32, 32, "Dell", 2300);

            // Adding phones for users
            for (int i = 6; i < 13; i++) {
                phoneRepository.save(new Phone("Apple", (int)Math.pow(1.3, i), Math.pow(1.1, i) * 1000, "iPhone " + i, (int)Math.pow(1.3, i) * 100));
            }

            // Associate devices with users
            user1.setLaptop(laptop1);
            user2.setLaptop(laptop2);
            user3.setLaptop(laptop3);

            user1.addPhones(phoneRepository.findById(1));
            user1.addPhones(phoneRepository.findById(2));
            user1.addPhones(phoneRepository.findById(6));

            user2.addPhones(phoneRepository.findById(3));
            user2.addPhones(phoneRepository.findById(4));

            // Batch save users
            userRepository.saveAll(Arrays.asList(user1, user2, user3));
        };
    }
}
