package edu.tecnocampus.labinternet.restafaris.restafarisapi.application;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CourseDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.EnrollmentDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.UserDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception.EntityNotFound;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Enrollment;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.User;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance.CourseRepository;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance.EnrollmentRepository;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    public UserService(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository){
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;

    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List<CourseDTO> getCreatedCourses(String userId) {
        return userRepository.findCoursesByUserDesc(userId).stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    public EnrollmentDTO buyCourse(String userId, String courseId){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFound(User.class, "id", userId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFound(Course.class, "id", courseId));

        Enrollment enrollment = new Enrollment(user, course);
        enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(enrollment);
    }
}
