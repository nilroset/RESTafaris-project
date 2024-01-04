package edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Enrollment;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Review;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@EnableTransactionManagement
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {


    Optional <Enrollment> findByUserAndCourse(User user, Course course);

    @Query("""
            select review from Enrollment e where e.course.id like :courseId
            """)
    List<Review> findReviewsOfCourse(String courseId);

    @Query("""
            select e.course from Enrollment e where e.user.id like :userId
            """)
    Optional<List<Course>> findCoursesByUser(String userId);

    @Query("""
            select e from Enrollment e where e.review is not null
            """)
    List<Enrollment> findAllWithReview();

    @Query("""
            select e from Enrollment e where e.course.id like :courseId
            """)
    List<Enrollment> findAllByCourse(String courseId);

    @Query("""
            select distinct e.course.creator from Enrollment e where YEAR(review.date) = :year
            """)
    List<User> findAllOfTheYear(int year);

    @Query("""
            select review.satisfaction from Enrollment e where e.course.creator.id like :id
            """)

    List<Integer> findAllSatisfactionsByTeacher(String id);
}
