package edu.tecnocampus.labinternet.restafaris.restafarisapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

@Entity
@Table(name = "enrollment")
@NoArgsConstructor
@Getter
@Setter
public class Enrollment {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    User user;

    @ManyToOne
    Course course;

    @ManyToMany
    List<Lesson> doneLessons;

    @ManyToOne
    Review review;

    Double price;

    LocalDate dateOfFinish;

    public Enrollment(User user, Course course){
        this.user = user;
        this.course = course;
        this.price = course.getCurrentPrice();
    }

    public void finishEnrollment(){
        this.dateOfFinish = LocalDate.now();
    }
}
