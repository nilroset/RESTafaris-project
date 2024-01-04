package edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Course;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Repository
@EnableTransactionManagement
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findAllByOrderByDateDesc();
    List<Review> findAllByOrderBySatisfactionDesc();
}
