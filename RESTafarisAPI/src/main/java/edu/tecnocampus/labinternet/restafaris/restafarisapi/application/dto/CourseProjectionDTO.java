package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Category;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CourseProjectionDTO {
    private String title;
    private String description;

    private String category;

    private String language;

    public CourseProjectionDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public CourseProjectionDTO(String title, String description, Category category, Language language){
        this(title, description);
        this.language = language.getName();
        this.category = category.getName();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
