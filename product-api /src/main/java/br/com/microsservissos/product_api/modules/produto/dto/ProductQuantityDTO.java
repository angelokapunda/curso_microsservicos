package br.com.microsservissos.product_api.modules.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDTO {

    private Integer productId;
    private Integer quantity;
}
