package br.com.microsservissos.product_api.modules.produto.service;

import br.com.microsservissos.product_api.modules.produto.model.Category;
import br.com.microsservissos.product_api.modules.produto.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save ( Category category) {
        return categoryRepository.save(category);
    }
}
