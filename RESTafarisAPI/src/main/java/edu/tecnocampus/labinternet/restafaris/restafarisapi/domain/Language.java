package edu.tecnocampus.labinternet.restafaris.restafarisapi.domain;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.LanguageDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "language")
public class Language {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;

    //ho hem de treure??
    @OneToMany(mappedBy = "language")
    private List<Course> courseList;

    public Language(LanguageDTO languageDTO){
        this.name = languageDTO.getName();
    }
}
