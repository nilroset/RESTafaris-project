package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Getter
public class LessonDTO {
    private String id;
    private String title;
    private String description;
    private double duration;
    private String videoUrl;
    private String courseId;
    //private int order;

    public LessonDTO(Lesson lesson){
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.description = lesson.getDescription();
        this.duration = lesson.getDuration();
        this.videoUrl = lesson.getVideoUrl();
        this.courseId = lesson.getCourse().getId();
        //this.order = lesson.getOrder();
    }
}
