package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryDTO {
    String id;
    String name;

    public CategoryDTO (Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
