package edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public interface LanguageRepository extends JpaRepository<Language, String> {
}
