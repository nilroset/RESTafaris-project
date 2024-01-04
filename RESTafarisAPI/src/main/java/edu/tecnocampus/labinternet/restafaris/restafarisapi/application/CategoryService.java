package edu.tecnocampus.labinternet.restafaris.restafarisapi.application;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CategoryDTO;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.domain.Category;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.persistance.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        categoryRepository.save(category);
        return new CategoryDTO(category);
    }

   public void deleteCategory(String id) throws Exception {
        Category category = getCategoryFromRepository(id);
        categoryRepository.deleteById(id);
    }

    private Category getCategoryFromRepository(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) throw new RuntimeException("Can't get category with id: " + id);
        return category.get();
    }
}
