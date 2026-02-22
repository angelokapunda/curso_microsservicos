package br.com.microsservissos.product_api.modules.produto.service;

import br.com.microsservissos.product_api.config.exception.ValidationException;
import br.com.microsservissos.product_api.modules.produto.model.Category;
import br.com.microsservissos.product_api.modules.produto.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        validateCategoryNameInformed(category);
        return categoryRepository.save(category);
    }

    public void validateCategoryNameInformed(Category category) {
        if (isEmpty(category.getDescription())) {
            throw new ValidationException("The category was not informed");
        }
    }
}


