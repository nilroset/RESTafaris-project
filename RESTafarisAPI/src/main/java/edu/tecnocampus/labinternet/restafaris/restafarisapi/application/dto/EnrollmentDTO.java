package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Enrollment;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EnrollmentDTO {
    private String id;
    private Review review;
    private String user_id;
    private String course_id;
    private double price;

    public EnrollmentDTO(Enrollment enrollment) {
        this.id = enrollment.getId();
        this.review = enrollment.getReview();
        this.user_id = enrollment.getUser().getId();
        this.course_id = enrollment.getCourse().getId();
        this.price = enrollment.getCourse().getCurrentPrice();
    }
}
