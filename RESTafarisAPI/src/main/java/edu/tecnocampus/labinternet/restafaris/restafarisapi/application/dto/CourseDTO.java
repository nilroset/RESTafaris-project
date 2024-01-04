package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CourseDTO {
    private String id;

    @Pattern(regexp = "^[A-Z].*", message = "Title must start with a capital letter")
    private String title;

    private String description;
    private LocalDate publication;
    private LocalDate lastUpdate;

    private String imageUrl;

    @Min(value = 0, message = "The value must be positive")
    private Double currentPrice;

    private Boolean availability;

    private String creator_id;
    private String category_id;
    private String language_id;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.publication = course.getPublication();
        this.lastUpdate = course.getLastUpdate();
        this.imageUrl = course.getImageUrl();
        this.currentPrice = course.getCurrentPrice();
        this.availability = course.isAvailability();
        //TODO: https://developer.spotify.com/documentation/web-api/reference/get-playlist
        this.creator_id = course.getCreator().getId();
        this.category_id = course.getCategory().getId();
        this.language_id = course.getLanguage().getId();
    }
}
