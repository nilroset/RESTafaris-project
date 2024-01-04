package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LessonProjectionDTO {
    private String title;
    private String description;

    public LessonProjectionDTO(Lesson lesson) {
        this.title = lesson.getTitle();
        this.description = lesson.getDescription();
    }
}
