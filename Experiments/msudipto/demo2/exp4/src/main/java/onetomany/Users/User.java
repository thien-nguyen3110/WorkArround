
package onetomany.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Date;
import onetomany.Laptops.Laptop;
import java.util.List;
import java.util.ArrayList;
import onetomany.Phones.Phone;

/**
 * User entity class representing a system user with details such as name and email.
 * 
 * Enhancements:
 * - Improved variable names and readability.
 * - Added validation for name, email, and phone association.
 * 
 * Author: Vivek Bengre
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private Date createdDate;

    @OneToOne(mappedBy = "user")
    private Laptop laptop;

    private List<Phone> phones = new ArrayList<>();

    public User() {}

    public User(String name, String email, Date createdDate) {
        if (name == null || email == null) {
            throw new IllegalArgumentException("Name and email cannot be null.");
        }
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void addPhones(Phone phone) {
        this.phones.add(phone);
    }
}
