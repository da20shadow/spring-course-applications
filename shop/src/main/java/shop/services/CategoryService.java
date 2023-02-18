package shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.models.entities.Category;
import shop.models.enums.CategoryEnum;
import shop.repositories.CategoryRepository;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryByName(CategoryEnum category) {
        Optional<Category> result = this.categoryRepository.findByName(category);

        if (result.isEmpty()){
            return null;
        }

        return result.get();
    }

    public void insertCategories() {
        if (this.categoryRepository.count() == 0){
            Arrays.stream(CategoryEnum.values()).forEach(c -> {
                Category category = new Category();
                category.setName(c);
                category.setDescription(c + " description...");
                this.categoryRepository.save(category);
            });
        }
    }

}
