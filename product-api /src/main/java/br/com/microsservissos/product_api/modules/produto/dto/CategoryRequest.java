package br.com.microsservissos.product_api.modules.produto.dto;

import org.springframework.stereotype.Component;

@Component
public class CategoryRequest {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
