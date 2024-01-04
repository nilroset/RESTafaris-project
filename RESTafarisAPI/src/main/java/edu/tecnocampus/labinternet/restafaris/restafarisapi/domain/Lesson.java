package edu.tecnocampus.labinternet.restafaris.restafarisapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "lesson")
@NoArgsConstructor
@Getter
@Setter
public class Lesson {
    @Id
    private String id = UUID.randomUUID().toString();
    private String title;
    private String description;
    private double duration;
    private String videoUrl;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    private Integer orders;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Course course;

    public Lesson(String title, String description, double duration, String videoUrl, Course course){
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.videoUrl = videoUrl;
        this.course = course;
        //this.creation = new Date();
        this.orders = 0;
    }
}
