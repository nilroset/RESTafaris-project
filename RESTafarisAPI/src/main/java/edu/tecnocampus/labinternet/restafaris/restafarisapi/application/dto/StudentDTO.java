package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentDTO {
    private String id;
    private String name;
    private int finishedCourses;

    public StudentDTO(String id, String name, int finishedCourses) {
        this.id = id;
        this.name = name;
        this.finishedCourses = finishedCourses;
    }
}
