package br.com.microsservissos.product_api.modules.produto.controller;

import br.com.microsservissos.product_api.config.exception.SuccessResponse;
import br.com.microsservissos.product_api.modules.produto.dto.CategoryRequest;
import br.com.microsservissos.product_api.modules.produto.dto.CategoryResponse;
import br.com.microsservissos.product_api.modules.produto.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse save (@RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @GetMapping()
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id) {
        return categoryService.findByIdResponse(id);
    }

    @GetMapping("/description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable String description) {
        return categoryService.findByDescription(description);
    }

    @PutMapping("/{id}")
    public CategoryResponse update (@RequestBody CategoryRequest categoryRequest, @PathVariable Integer id) {
        return categoryService.update(categoryRequest, id);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete (@PathVariable Integer id) {
        return categoryService.delete(id);
    }
}
