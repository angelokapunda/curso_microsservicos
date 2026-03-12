package br.com.microsservissos.product_api.modules.produto.service;

import br.com.microsservissos.product_api.config.exception.SuccessResponse;
import br.com.microsservissos.product_api.config.exception.ValidationException;
import br.com.microsservissos.product_api.modules.produto.dto.CategoryRequest;
import br.com.microsservissos.product_api.modules.produto.dto.CategoryResponse;
import br.com.microsservissos.product_api.modules.produto.model.Category;
import br.com.microsservissos.product_api.modules.produto.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public CategoryResponse save(CategoryRequest categoryRequest) {
        validateCategoryNameInformed(categoryRequest);
        var category = categoryRepository.save(Category.of(categoryRequest));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest categoryRequest, Integer id) {
        validateCategoryNameInformed(categoryRequest);
        validateInformedId(id);
        var category = Category.of(categoryRequest);
        category.setId(id);
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    public CategoryResponse findByIdResponse(Integer id) {
        return CategoryResponse.of(findById(id));
    }
    public Category findById (Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no Category for the given ID"));
    }
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> CategoryResponse.of(category))
                .toList();
    }

    public List<CategoryResponse> findByDescription(String description) {
        if (isEmpty(description)) {
            throw new ValidationException("The category description must be informed");
        }
        return categoryRepository.findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(category -> CategoryResponse.of(category))
                .toList();
    }

    public void validateCategoryNameInformed(CategoryRequest categoryRequest) {
        if (isEmpty(categoryRequest.getDescription())) {
            throw new ValidationException("The category was not informed");
        }
    }

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        if (productService.existsByCategoryId(id)) {
            throw new ValidationException("You cannot delete this supplier because it's already defined by a product.");
        }
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The Category was delete");
    }

    private void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("The Category Id must be informed");
        }
    }
}


