package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LanguageDTO {
    private String id;
    private String name;

    public LanguageDTO (Language language){
        this.id = language.getId();
        this.name = language.getName();
    }
}
