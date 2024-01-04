package edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CourseProjectionDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Category;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Language;
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
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query("""
        select new edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CourseProjectionDTO(c.title, c.description)
        from Course c
        where lower(c.title) like lower(concat('%', :text, '%')) or lower(c.description) like lower(concat('%', :text, '%')) order by c.title asc""")
    List<CourseProjectionDTO> findByTitleOrDescription(@Param("text") String text);

    List<Course> findByAvailabilityTrueOrderByTitle();
    /*List<Course> findByTeacherIdOrderByDate(String teacherId);*/
    List<Course> findAllByOrderByTitle();
    boolean existsByTitle(String title);

    @Query("SELECT DISTINCT c FROM Course c WHERE c.language.id IN :languages")
    List<Course> findCoursesByLanguages(@Param("languages") Optional<List<String>> languages);
    @Query("SELECT DISTINCT c FROM Course c WHERE c.category.id IN :categories")
    List<Course> findCoursesByCategories(@Param("categories") Optional<List<String>> categories);

    @Query("""
        SELECT c 
        FROM Course c  
        WHERE c.language.id IN :languages AND c.category.id IN :categories""")
    List<Course> findCoursesByLanguagesAndCategories(
            @Param("languages") List<String> languages,
            @Param("categories") List<String> categories
    );

    @Query("""
           select c.title, c.description, c.imageUrl, c.creator, c.category, c.language
           from Course c
           WHERE c.availability = true and c.creator.id = ?1
           order by c.lastUpdate desc, c.publication desc 
           """)
    List<Course> findCoursesByUserDesc(String userId);

    @Query("""
           select c
           from Course c
           WHERE c.creator.id like :userId """)
    Optional<List<Course>> findCoursesByUser(String userId);

    //List<Course> findCoursesByCreatorIdAndOrderByLastUpdateDesc(String userId);
}
