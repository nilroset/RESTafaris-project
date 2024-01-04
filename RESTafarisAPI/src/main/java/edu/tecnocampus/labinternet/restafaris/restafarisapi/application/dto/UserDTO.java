package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Gender;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserDTO {
    private String id;
    private String username;
    private String name;
    private String secondName;
    private String thirdName;
    private String email;
    private Gender gender;
    private List<Course> courses;

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.secondName = user.getSecondName();
        this.thirdName = user.getThirdName();
        this.email = user.getEmail();
        this.gender = user.getGender();
       // this.courses = user.getCourses();
    }
}
