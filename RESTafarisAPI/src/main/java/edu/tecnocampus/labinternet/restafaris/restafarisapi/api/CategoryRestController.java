package edu.tecnocampus.labinternet.restafaris.restafarisapi.api;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.CategoryService;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryRestController {
    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return categoryService.createCategory(categoryDTO);
    }

   @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable String id) throws Exception {
        categoryService.deleteCategory(id);
    }
}
