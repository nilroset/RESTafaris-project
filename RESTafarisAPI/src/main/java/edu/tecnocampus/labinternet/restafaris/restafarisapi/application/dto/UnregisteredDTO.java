package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Category;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Language;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UnregisteredDTO {
    private String title;
    private String description;
    private String teacherName;
    private double duration;
    private String categoryName;
    private String languageName;
    private List<LessonProjectionDTO> lessons;

    public UnregisteredDTO(Course course, List<Lesson> lessons, Category category, Language language){
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.teacherName = course.getCreator().getName();
        this.duration = lessons.stream().mapToDouble(Lesson::getDuration).sum();
        this.categoryName = category.getName();
        this.languageName = language.getName();

        if(lessons == null) {
            this.lessons = null;
        } else {
            if(lessons.isEmpty()) {
                this.lessons = null;
            } else {
                this.lessons = lessons.stream().map(LessonProjectionDTO::new).collect(Collectors.toList());
            }
        }
    }
}
