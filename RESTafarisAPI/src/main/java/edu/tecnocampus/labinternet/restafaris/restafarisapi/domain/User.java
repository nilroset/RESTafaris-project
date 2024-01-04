package edu.tecnocampus.labinternet.restafaris.restafarisapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User {
    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(unique = true)
    private String username;

    private String name;
    private String secondName;
    private String thirdName;

    @OneToMany(mappedBy = "creator")
    //(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    public User(String username, String name, String secondName, String thirdName, String email, String gender) {
        this.username = username;
        this.name = name;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.email = email;
        this.gender = Gender.valueOf(gender);
        this.courses = new ArrayList<>();
    }
}