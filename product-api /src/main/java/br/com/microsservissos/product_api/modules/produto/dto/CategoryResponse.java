package br.com.microsservissos.product_api.modules.produto.dto;

import br.com.microsservissos.product_api.modules.produto.model.Category;
import org.springframework.beans.BeanUtils;

public class CategoryResponse {

    private Integer id;
    private String description;

    public CategoryResponse of (Category category) {
        var response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        return response;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
