package br.com.microsservissos.product_api.modules.produto.dto;

import br.com.microsservissos.product_api.modules.produto.model.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequest {

    private String description;

    public String getDescription() {
        return description;
    }

    public Category toObject(CategoryRequest categoryRequest) {
        var category = new Category();
        BeanUtils.copyProperties(category, categoryRequest, "id");
        return category;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
