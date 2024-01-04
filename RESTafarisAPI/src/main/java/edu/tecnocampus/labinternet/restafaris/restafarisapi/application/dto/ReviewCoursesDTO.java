package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewCoursesDTO {
    private String courseName;
    private Review review;

    public ReviewCoursesDTO(String courseName, Review review) {
        this.courseName = courseName;
        this.review = review;
    }
}
