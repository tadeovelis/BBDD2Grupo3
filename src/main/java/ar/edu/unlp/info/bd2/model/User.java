package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;


@Entity
@Document(indexName = "user")
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    @Column(unique=true, nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;
    private Date dayOfBirth;


    public User() {};

    public User(String email, String fullname, String password, Date dayOfBirth) {
        setFullName(fullname);
        setEmail(email);
        setPassword(password);
        setDateOfBirth(dayOfBirth);
    };

    public String getFullname() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDateOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }
    
}
