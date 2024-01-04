package edu.tecnocampus.labinternet.restafaris.restafarisapi.domain;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.ReviewDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "review")
public class Review {
    @Id
    private String id = UUID.randomUUID().toString();
    private String title;
    private String content;
    private int satisfaction;
    private LocalDate date;

    @ManyToOne
    private User user;


    public Review(ReviewDTO reviewDTO){
        this.title = reviewDTO.getTitle();
        this.content = reviewDTO.getContent();
        this.satisfaction = reviewDTO.getSatisfaction();
        this.date = LocalDate.now();
    }

    public void updateReview(ReviewDTO reviewDto) {
        this.title = reviewDto.getTitle();
        this.content = reviewDto.getContent();
        this.satisfaction = reviewDto.getSatisfaction();
        this.date = LocalDate.now();
    }
}
