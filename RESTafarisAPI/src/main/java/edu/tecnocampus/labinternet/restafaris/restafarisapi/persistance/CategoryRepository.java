package edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public interface CategoryRepository extends JpaRepository<Category, String> {
}
