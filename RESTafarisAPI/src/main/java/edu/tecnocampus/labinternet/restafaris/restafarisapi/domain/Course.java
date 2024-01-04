package edu.tecnocampus.labinternet.restafaris.restafarisapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CourseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(unique = true)
    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publication;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdate;

    private String imageUrl;
    private double currentPrice;
    private boolean availability;

    @ManyToOne
    //@JoinColumn(name = "user_id")
    private User creator;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Category category;

    @ManyToOne
    private Language language;


    //un course puede tener una UNICA categoria pero una categoria puede tenir molts cursos
    //MenyToOne
    /*@ManyToOne
    private Category category;

    @MenyToOne
    private Language language;
    */

    public Course(String title, String description, String imageUrl, User creator, Category category, Language language) {
        this.title = title;
        this.description = description;
        this.publication = LocalDate.now();
        this.lastUpdate = null;
        this.imageUrl = imageUrl;
        this.currentPrice = 0;
        this.availability = false;
        this.creator = creator;
        this.category = category;
        this.language = language;
    }

    public LocalDate setNewDate(LocalDate newDate) {
        return this.lastUpdate = newDate;
    }
    //#3
    public void updateCourse(CourseDTO courseDTO) {
        checkNullArgument(courseDTO);
        this.title = courseDTO.getTitle();
        this.description = courseDTO.getDescription();
        this.imageUrl = courseDTO.getImageUrl();
    }

    //#4
    public void updateCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    //#5
    public void updateAvailability(Boolean availability) {
        this.availability = availability;
    }

    private void checkNullArgument(Object argument) {
        if (argument == null)
            throw new IllegalArgumentException("Null argument");
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publication=" + publication +
                ", lastUpdate=" + lastUpdate +
                ", imageUrl='" + imageUrl + '\'' +
                ", currentPrice=" + currentPrice +
                ", availability=" + availability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Double.compare(course.currentPrice, currentPrice) == 0 && availability == course.availability && Objects.equals(title, course.title) && Objects.equals(description, course.description) && Objects.equals(publication, course.publication) && Objects.equals(lastUpdate, course.lastUpdate) && Objects.equals(imageUrl, course.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, publication, lastUpdate, imageUrl, currentPrice, availability);
    }
}
