package edu.tecnocampus.labinternet.restafaris.restafarisapi.domain;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CategoryDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "category")
public class Category {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;

    //ho hem de treure??
    @OneToMany(mappedBy = "category")
    private List<Course> courseList;

    public Category(CategoryDTO categoryDTO) {
        this.name = categoryDTO.getName();
    }

    public static class Enrollment {
    }
}
