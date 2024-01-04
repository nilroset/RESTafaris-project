package edu.tecnocampus.labinternet.restafaris.restafarisapi.api;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.UserService;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CourseDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.EnrollmentDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return userService.getAllUsers();}

    @GetMapping("/users/{id}/courses")
    public List<CourseDTO> getCreatedCourses(@PathVariable String id) {
        return userService.getCreatedCourses(id);
    }

    @GetMapping("/users/{id}/buyCourse")
    public EnrollmentDTO buyCourse(@PathVariable String id, @RequestBody String courseId){
        return userService.buyCourse(id, courseId);
    }
}
