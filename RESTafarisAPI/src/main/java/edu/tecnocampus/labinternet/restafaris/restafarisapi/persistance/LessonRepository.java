package edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;

@Repository
@EnableTransactionManagement
public interface LessonRepository extends JpaRepository<Lesson, String> {
    Optional<List<Lesson>> findAllByCourseIdOrderByOrdersAscCreationAsc(String courseId);
    Optional<List<Lesson>> findAllByCourseId(String courseId);
    Optional <List<Lesson>> findAllByCourseIdOrderByCreationAsc(String courseId);

}
