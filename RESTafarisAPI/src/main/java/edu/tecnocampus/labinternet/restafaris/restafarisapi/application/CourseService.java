package edu.tecnocampus.labinternet.restafaris.restafarisapi.application;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.*;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception.CourseNotFound;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception.EntityNotFound;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.*;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private LanguageRepository languageRepository;
    private LessonRepository lessonRepository;
    private EnrollmentRepository enrollmentRepository;
    private ReviewRepository reviewRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, CategoryRepository categoryRepository, LanguageRepository languageRepository, LessonRepository lessonRepository, EnrollmentRepository enrollmentRepository, ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.languageRepository = languageRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewRepository = reviewRepository;

    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        User creator = userRepository.findById(courseDTO.getCreator_id())
                .orElseThrow(() -> new EntityNotFound(User.class, "id", courseDTO.getCreator_id()));
        Category category = categoryRepository.findById(courseDTO.getCategory_id())
                .orElseThrow(() -> new EntityNotFound(Category.class, "id", courseDTO.getCategory_id()));
        Language language = languageRepository.findById(courseDTO.getLanguage_id())
                .orElseThrow(() -> new EntityNotFound(Language.class, "id", courseDTO.getLanguage_id()));

        Course course = new Course(courseDTO.getTitle(), courseDTO.getDescription(), courseDTO.getImageUrl(), creator, category, language);
        courseRepository.save(course);
        return new CourseDTO(course);
    }

    public ReturningDTO getCourse(String id, String userName) throws Exception {
        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFound(Course.class, "id", id));
        List<Lesson> lessons = lessonRepository.findAllByCourseIdOrderByOrdersAscCreationAsc(id).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", id));
        return new ReturningDTO(course, lessons, null);
    }


    @Transactional
    public CourseDTO updateCourse(String id, CourseDTO courseDTO) throws Exception {
        Course course = courseRepository.findById(id).orElseThrow(()-> new CourseNotFound(id));
        if(course.isAvailability()) throw new Exception("The course must be unavailable to modify it");
        course.updateCourse(courseDTO);
        course.setNewDate(LocalDate.now());
        return new CourseDTO((course));
    }

    @Transactional
    public CourseDTO updatePrice(String id, CourseDTO courseDTO) throws Exception {
        Course course = courseRepository.findById(id).orElseThrow(()-> new CourseNotFound(id));
        if(course.isAvailability()) throw new Exception("The course must be unavailable to modify it");
        course.updateCurrentPrice(courseDTO.getCurrentPrice());
        course.setNewDate(LocalDate.now());
        return new CourseDTO(course);
    }

    @Transactional
    public CourseDTO updateAvailability(String id, CourseDTO courseDTO) throws Exception {
        Course course = courseRepository.findById(id).orElseThrow(()->new CourseNotFound(id));
        //if(lessonRepository.findAllByCourseId(courseDTO.getId()).isEmpty()) throw new Exception("The course has no lessons");
        course.updateAvailability(courseDTO.getAvailability());
        course.setNewDate(LocalDate.now());
        return new CourseDTO(course);
    }

    public List<ReturningDTO> getAvailableSortedCourses() throws Exception {
        List<ReturningDTO> returning = new ArrayList<>();
        List<Course> courses = courseRepository.findByAvailabilityTrueOrderByTitle();
        for (Course course : courses) {
            List<Review> reviews = enrollmentRepository.findReviewsOfCourse(course.getId());
            ReturningDTO returningDTO = new ReturningDTO(course,null, reviews);
            returning.add(returningDTO);
        }
        return returning;
    }

    public List<CourseProjectionDTO> getMatchedTitleAndDescription(String text) {
        return courseRepository.findByTitleOrDescription(text);
    }

    public List<CourseDTO> getCoursesFromCategoryAndLanguage(Optional<List<String>> categories, Optional<List<String>> languages) {
        if(categories.isEmpty()) return courseRepository.findCoursesByLanguages(languages).stream().map(CourseDTO::new).collect(Collectors.toList());
        if(languages.isEmpty()) return courseRepository.findCoursesByCategories(categories).stream().map(CourseDTO::new).collect(Collectors.toList());
        return courseRepository.findCoursesByLanguagesAndCategories(languages.get(), categories.get()).stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByUserId(String userId){
        return courseRepository.findCoursesByUserDesc(userId).stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    public LanguageDTO createLanguage(LanguageDTO languageDTO) {
        Language language = new Language(languageDTO);
        languageRepository.save(language);
        return new LanguageDTO(language);
    }

    public LessonDTO createLesson(LessonDTO lessonDTO){
        Course course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(()-> new EntityNotFound(Course.class, "id", lessonDTO.getCourseId()));

        Lesson lesson = new Lesson(lessonDTO.getTitle(), lessonDTO.getDescription(), lessonDTO.getDuration(), lessonDTO.getVideoUrl(), course);
        course.setNewDate(LocalDate.now());
        lessonRepository.save(lesson);
        return new LessonDTO(lesson);
    }

    public List<LessonDTO> getLessonsByCourseId(String name, String courseId) throws Exception{
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new EntityNotFound(Course.class, "id", courseId));
        List <Course> courses = enrollmentRepository.findCoursesByUser(user.getId()).orElseThrow(()-> new EntityNotFound(User.class, "user", user));

        if(!courses.contains(course)) throw new Exception("The user is not enrolled in this course");
        return lessonRepository.findAllByCourseIdOrderByCreationAsc(courseId).orElseThrow(() -> new EntityNotFound(Course.class, "courseId", courseId))
                .stream().map(LessonDTO::new).collect(Collectors.toList());
    }


    @Transactional
    public List<LessonDTO> updateLessonList(String courseId, List<String> newOrder){
        List<LessonDTO> oldLessonDTOList = lessonRepository.findAllByCourseId(courseId).orElseThrow(()-> new EntityNotFound(Course.class, "course", courseId)).stream().map(LessonDTO::new).toList();
        List<LessonDTO> newOrderedList = new ArrayList<>();
        for (int i = 0; i<oldLessonDTOList.size(); i++){
            String id = newOrder.get(i);
            Lesson lesson = lessonRepository.findById(id).orElseThrow();
            lesson.setOrders(i);
            LessonDTO lessonDTO = oldLessonDTOList.stream().filter(lessonn -> lessonn.getId().equals(id)).findFirst().get();
            newOrderedList.add(lessonDTO);
        }
        return newOrderedList;
    }

    public List<LessonDTO> getLessonsByCourseIdOrdered(String name, String courseId) throws Exception{
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new EntityNotFound(Course.class, "id", courseId));
        List <Course> courses = enrollmentRepository.findCoursesByUser(user.getId()).orElseThrow(()-> new EntityNotFound(User.class, "user", user));

        if(!courses.contains(course)) throw new Exception("The user is not enrolled in this course");
        List<Lesson> lessons = lessonRepository.findAllByCourseIdOrderByOrdersAscCreationAsc(courseId).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", courseId));
        return lessons.stream().map(LessonDTO::new).collect(Collectors.toList());
    }


    @Transactional
    public EnrollmentDTO markLessonAsDone(String id, String name) throws Exception {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(()-> new EntityNotFound(Lesson.class, "id", id));
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, lesson.getCourse()).orElseThrow(() -> new EntityNotFound(Enrollment.class, "user", user.getId()));
        Course course = enrollment.getCourse();

        List <Lesson> allLesons = lessonRepository.findAllByCourseIdOrderByCreationAsc(course.getId()).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", course.getId()));

        enrollment.getDoneLessons().add(lesson);

        if(allLesons.size() == enrollment.getDoneLessons().size()) enrollment.finishEnrollment();
        return new EnrollmentDTO(enrollment);
    }

    public double buyCourse(String id) {
        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFound(Course.class, "id", id));
        //Here should go the transaction of the course
        return course.getCurrentPrice();
    }


    public ReviewDTO addReview(String id, ReviewDTO review, String name) {
        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFound(Course.class, "id", id));
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, course).orElseThrow(()-> new EntityNotFound(Enrollment.class, "user", user.getId()));
        if(enrollment.getReview() != null) throw new IllegalArgumentException("You have already reviewed this course");

        List <Lesson> lessons = lessonRepository.findAllByCourseIdOrderByCreationAsc(course.getId()).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", course.getId()));
        if(lessons.size()/2 >= enrollment.getDoneLessons().size()) throw new IllegalArgumentException("You have to complete at least half of the course to review it");

        Review newReview = new Review(review);
        reviewRepository.save(newReview);
        enrollment.setReview(newReview);

        enrollmentRepository.save(enrollment);
        return new ReviewDTO(newReview);
    }

    @Transactional
    public ReviewDTO updateReview(String id, ReviewDTO reviewDto, String name) throws Exception{
        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFound(Course.class, "id", id));
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, course).orElseThrow(()-> new EntityNotFound(Enrollment.class, "user", user.getId()));
        Review review = reviewRepository.findById(enrollment.getReview().getId()).orElseThrow(()->new Exception("You have not reviewed this course"));

        review.updateReview(reviewDto);

        return new ReviewDTO(review);
    }

    public List<ReturningDTO> getMyCourses(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        List<Course> courses = enrollmentRepository.findCoursesByUser(user.getId()).orElseThrow(() -> new EntityNotFound(User.class, "user", user.getId()));
        return courses.stream().map(course -> {
            List<Lesson> lessons = lessonRepository.findAllByCourseIdOrderByOrdersAscCreationAsc(course.getId()).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", course.getId()));
            List<Review> reviews = enrollmentRepository.findReviewsOfCourse(course.getId());
            try {
                return new ReturningDTO(course, lessons, reviews);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public List<CourseDTO>getMyCreatedCourses(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        List<Course> courses = courseRepository.findCoursesByUser(user.getId()).orElseThrow(() -> new EntityNotFound(User.class, "user", user.getId()));
        return courses.stream().map(CourseDTO::new).collect(Collectors.toList());
    }


    public EnrollmentDTO purchaseCourse(String name, String courseId){
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFound(Course.class, "id", courseId));
        Enrollment enrollment = new Enrollment(user, course);
        enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(enrollment);
    }

    public List<CourseDTO> getMyNotFinishedCourses(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(()-> new EntityNotFound(User.class, "username", name));
        List<Course> courses = enrollmentRepository.findCoursesByUser(user.getId()).orElseThrow(() -> new EntityNotFound(User.class, "user", user.getId()));
        List<Course> returnCourses = new ArrayList<>();
        for (Course course: courses) {
            List<Lesson> lessonsOfCourse = lessonRepository.findAllByCourseId(course.getId()).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", course.getId()));
            List<Lesson> doneLessons = enrollmentRepository.findByUserAndCourse(user, course).orElseThrow(()-> new EntityNotFound(Enrollment.class, "user", user.getId())).getDoneLessons();
            if(lessonsOfCourse.size() != doneLessons.size()) returnCourses.add(course);
        }
        return returnCourses.stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    public List<UnregisteredDTO> getAvailableCoursesUnregistered() {
        List<Course> courses = courseRepository.findByAvailabilityTrueOrderByTitle();
        List<UnregisteredDTO> unregisteredDTOS = new ArrayList<>();
        for (Course course: courses) {
            List<Lesson>  lessonsOfCourse = lessonRepository.findAllByCourseId(course.getId()).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", course.getId()));
            UnregisteredDTO unregisteredDTO = new UnregisteredDTO(course, lessonsOfCourse, course.getCategory(), course.getLanguage());
            unregisteredDTOS.add(unregisteredDTO);
        }
        return unregisteredDTOS;
    }

    public List<ReviewCoursesDTO> getAllReviews(boolean orderByDate){
        List<Review> reviews;
        List<ReviewCoursesDTO> reviewCoursesDTOS = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findAllWithReview();

        if(orderByDate) reviews = reviewRepository.findAllByOrderByDateDesc();
        else reviews = reviewRepository.findAllByOrderBySatisfactionDesc();

        for(Review review: reviews) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getReview().getId().equals(review.getId())) {
                    ReviewCoursesDTO reviewCoursesDTO = new ReviewCoursesDTO(enrollment.getCourse().getTitle(), review);
                    reviewCoursesDTOS.add(reviewCoursesDTO);
                    break;
                }
            }
        }
        return reviewCoursesDTOS;
    }

    public List<UserDTO> getStudentsByCourseId(String id, String name) {
        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFound(Course.class, "id", id));
        List <Enrollment> enrollments = enrollmentRepository.findAllByCourse(course.getId());
        List <User> users = new ArrayList<>();
        for (Enrollment enrollment: enrollments) {
            if(enrollment.getDateOfFinish() == null){
                users.add(enrollment.getUser());
            }else if(Period.between(enrollment.getDateOfFinish(), LocalDate.now()).getDays() < 60){
                users.add(enrollment.getUser());
            }
        }
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List<TeacherDTO> bestXTeachers(int x, int year) {
        List<User> teachers = enrollmentRepository.findAllOfTheYear(year);

        if(teachers.size() < x) throw new IllegalArgumentException("There are not enough teachers");
        List<TeacherDTO> teacherDTOS = new ArrayList<>();

        for (User teacher: teachers) {
            List<Integer> allSatisfactions = enrollmentRepository.findAllSatisfactionsByTeacher(teacher.getId());
            double average = allSatisfactions.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
            teacherDTOS.add(new TeacherDTO(teacher.getId(), teacher.getName(), average));
        }
        teacherDTOS.sort(Comparator.comparing(TeacherDTO::getAverageRating).reversed());

        return teacherDTOS.subList(0, x);
    }

    public List<StudentDTO> studentsMostFinishedCourses(Integer x) {
        List<User> users = userRepository.findAll();
        List<StudentDTO> returning = new ArrayList<>();

        if(users.size() < x) throw new IllegalArgumentException("There are not enough students");

        for (User user: users) {
            List<Course> courses = enrollmentRepository.findCoursesByUser(user.getId()).orElseThrow(() -> new EntityNotFound(User.class, "user", user.getId()));
            int finishedCourses = 0;
            for (Course course: courses) {
                List<Lesson> lessonsOfCourse = lessonRepository.findAllByCourseId(course.getId()).orElseThrow(()-> new EntityNotFound(Course.class, "courseId", course.getId()));
                List<Lesson> doneLessons = enrollmentRepository.findByUserAndCourse(user, course).orElseThrow(()-> new EntityNotFound(Enrollment.class, "user", user.getId())).getDoneLessons();
                if(lessonsOfCourse.size() == doneLessons.size()) finishedCourses++;
            }
            returning.add(new StudentDTO(user.getId(), user.getName(), finishedCourses));
        }
        returning.sort(Comparator.comparing(StudentDTO::getFinishedCourses).reversed());

        return returning.subList(0, x);
    }
}