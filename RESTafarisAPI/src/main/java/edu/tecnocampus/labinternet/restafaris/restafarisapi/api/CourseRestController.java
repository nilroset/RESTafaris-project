package edu.tecnocampus.labinternet.restafaris.restafarisapi.api;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.CourseService;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.*;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Category;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Language;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Review;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class CourseRestController {

    private CourseService courseController;

    public CourseRestController(CourseService courseController) { this.courseController = courseController; }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/courses")
    public CourseDTO createCourse(@Valid @RequestBody CourseDTO courseDTO) throws Exception {
        return courseController.createCourse(courseDTO);
    }

    @GetMapping("/courses/{id}")
    public ReturningDTO getCourse(@PathVariable String id, Principal user) throws Exception {
        return courseController.getCourse(id, user.getName());
    }

    @PutMapping("/courses/{id}")
    public CourseDTO updateCourse(@PathVariable String id, @Valid @RequestBody CourseDTO courseDTO) throws Exception {
        return courseController.updateCourse(id, courseDTO);
    }

    @PatchMapping("/courses/{id}/price")
    public CourseDTO updatePrice(@PathVariable String id, @Valid @RequestBody CourseDTO courseDTO) throws Exception {
        return courseController.updatePrice(id, courseDTO);
    }

    @PatchMapping("/courses/{id}/availability")
    public CourseDTO updateAvailability(@PathVariable String id, @RequestBody CourseDTO courseDTO) throws Exception {
        return courseController.updateAvailability(id, courseDTO);
    }

    @GetMapping("/courses")
    public List<ReturningDTO> getAvailableSortedCourses() throws Exception {
        return courseController.getAvailableSortedCourses();
    }

    @GetMapping("/courses/search")
    @ResponseBody
    public List<CourseProjectionDTO> getMatchedTextAndDescription(@RequestParam(name = "text")String text){
        return courseController.getMatchedTitleAndDescription(text);
    }

    @GetMapping("/courses/categoriesLanguages")
    public List<CourseDTO> getCoursesFromCategoryAndLanguage(@RequestParam(name = "category") Optional<List<String>> categories, @RequestParam(name = "language")Optional<List<String>> language) throws Exception{
        if(categories.isEmpty() && language.isEmpty())
            throw new Exception("You have to specify at least one parameter");
        System.out.println(categories);
        System.out.println(language);
        return courseController.getCoursesFromCategoryAndLanguage(categories, language);
    }

    @PostMapping("/languages")
    public LanguageDTO createLanguage(@Valid @RequestBody LanguageDTO languageDTO){
        return courseController.createLanguage(languageDTO);
    }

    @PostMapping("/lessons")
    public LessonDTO createLesson(@RequestBody LessonDTO lessonDTO){
        return courseController.createLesson(lessonDTO);
    }

    @GetMapping("/lessons/{id}")
    public List<LessonDTO> getLessonsByCourseId(Principal user, @PathVariable String id) throws Exception {
        return courseController.getLessonsByCourseId(user.getName(),id);
    }

    @GetMapping("/lessons/{id}/ordered")
    public List<LessonDTO> getOrderedLessons(Principal user, @PathVariable String id) throws Exception{
        return courseController.getLessonsByCourseIdOrdered(user.getName(), id);
    }
    
    @PutMapping("/lessons/{id}/order")
    public List<LessonDTO> updateLessonsOrder(@PathVariable String id, @RequestBody List<String> lessons){
        return courseController.updateLessonList(id, lessons);
    }

    @PatchMapping("/lessons/{id}/markAsDone")
    public EnrollmentDTO markLessonAsDone(@PathVariable String id, Principal user) throws Exception{
        return courseController.markLessonAsDone(id, user.getName());
    }

    @PutMapping("/course/{id}/review")
    public ReviewDTO addReview(@PathVariable String id, @Valid @RequestBody ReviewDTO review, Principal user) throws Exception{
        return courseController.addReview(id, review, user.getName());
    }

    @PatchMapping("/course/{id}/review")
    public ReviewDTO updateReview(@PathVariable String id, @Valid @RequestBody ReviewDTO review, Principal user) throws Exception{
        return courseController.updateReview(id, review, user.getName());
    }

    @GetMapping("/courses/myCourses")
    public List<ReturningDTO> getMyCourses(Principal user) throws Exception{
        return courseController.getMyCourses(user.getName());
    }

    @GetMapping("/courses/getMyCreatedCourses")
    public List<CourseDTO> getMyCreatedCourses(Principal user) throws Exception{
        return courseController.getMyCreatedCourses(user.getName());
    }

    @PostMapping("/course/{idCourse}/purchase")
    public EnrollmentDTO purchaseCourse(Principal user, @PathVariable String idCourse){
        return courseController.purchaseCourse(user.getName(), idCourse);
    }
    @GetMapping("/courses/myCourses/notFinished")
    public List<CourseDTO> getMyNotFinishedCourses(Principal user) throws Exception{
        return courseController.getMyNotFinishedCourses(user.getName());
    }
    @GetMapping("/courses/unregistered")
    public List<UnregisteredDTO> getAvailableCoursesUnregistered() throws Exception {
        return courseController.getAvailableCoursesUnregistered();
    }

    @GetMapping("/reviews")
    public List<ReviewCoursesDTO> getAllReviews(@RequestParam(defaultValue= "true") boolean orderByDate) {
        return courseController.getAllReviews(orderByDate);
    }

    @GetMapping("/courses/{id}/students")
    public List<UserDTO> getStudentsByCourseId(@PathVariable String id, Principal user) {
        return courseController.getStudentsByCourseId(id, user.getName());
    }

    @GetMapping("/teachers")
    public List<TeacherDTO> bestXTeachersInAYear(@RequestParam(defaultValue = "5") Integer x, @RequestParam Integer year) {
        return courseController.bestXTeachers(x, year);
    }

    @GetMapping("/students")
    public List<StudentDTO> studentsMostFinishedCourses(@RequestParam(defaultValue = "5") Integer x) {
        return courseController.studentsMostFinishedCourses(x);
    }
}