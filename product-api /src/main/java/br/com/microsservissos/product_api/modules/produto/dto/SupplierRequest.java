package br.com.microsservissos.product_api.modules.produto.dto;

import org.springframework.stereotype.Component;

@Component
public class SupplierRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
