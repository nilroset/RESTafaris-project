package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ReviewDTO {
    private String id;
    private String title;
    private String content;

    @Min(value = 0, message = "The value must be positive")
    @Max(value = 5, message = "The value should not be greater than 5")
    private int satisfaction;

    private LocalDate date;

    public ReviewDTO(Review review){
        this.id = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.satisfaction = review.getSatisfaction();
        this.date = review.getDate();
    }
}
