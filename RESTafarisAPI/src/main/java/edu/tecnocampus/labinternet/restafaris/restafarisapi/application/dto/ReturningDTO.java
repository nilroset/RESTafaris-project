package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Lesson;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ReturningDTO {
    private String id;
    private String title;
    private String description;
    private LocalDate publication;
    private LocalDate lastUpdate;
    private String imageUrl;
    private Double currentPrice;
    private Boolean availability;

    private String creatorId;
    private String categoryId;
    private String languageId;

    private List<LessonDTO> lessons;
    private List<ReviewDTO> reviews;

    public ReturningDTO(Course course, List<Lesson> lessons, List<Review> reviews) throws JsonProcessingException {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.publication = course.getPublication();
        this.lastUpdate = course.getLastUpdate();
        this.imageUrl = course.getImageUrl();
        this.currentPrice = course.getCurrentPrice();
        this.availability = course.isAvailability();
        this.creatorId = course.getCreator().getId();
        this.categoryId = course.getCategory().getId();
        this.languageId = course.getLanguage().getId();

        if(lessons == null) {
            this.lessons = null;
            if(reviews == null) {
                this.reviews = null;
            }
            else {
                this.reviews = reviews.stream().map(review -> new ReviewDTO(review)).collect(Collectors.toList());
            }
        } else {
            //this.reviews = null;
            if(lessons.isEmpty()) {
                this.lessons = null;
            } else {
                this.lessons = lessons.stream().map(lesson -> new LessonDTO((Lesson) lesson)).collect(Collectors.toList());
            }
            if(reviews == null) {
                this.reviews = null;
            }
            else {
                this.reviews = reviews.stream().map(review -> new ReviewDTO(review)).collect(Collectors.toList());;
            }
        }
    }
}
