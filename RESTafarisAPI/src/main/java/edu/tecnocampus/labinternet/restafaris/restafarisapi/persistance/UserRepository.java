package edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CourseDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;

@Repository
@EnableTransactionManagement
public interface UserRepository extends JpaRepository<User, String> {

    List<Course> findCoursesByIdOrderByCoursesLastUpdateDesc(String userId);

    //@Query("SELECT c FROM Course c " +
    //            "WHERE c.language IN :languages " +
    //            "AND c.category IN :categories")
    @Query("""
            select c from Course c where c.creator.id like :userId order by c.lastUpdate desc, c.publication desc
            """)
    List<Course> findCoursesByUserDesc(@Param("userId") String userId);

    Optional <User> findByUsername(String username);

}
