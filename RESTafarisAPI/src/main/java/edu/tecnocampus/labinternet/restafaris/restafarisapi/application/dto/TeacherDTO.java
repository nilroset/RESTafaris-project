package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TeacherDTO {
    private String teacherId;
    private String teacherName;
    private double averageRating;

    public TeacherDTO(String teacherId, String teacherName, double averageRating) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.averageRating = averageRating;
    }
}
