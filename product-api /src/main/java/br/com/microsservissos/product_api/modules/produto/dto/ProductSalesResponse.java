package br.com.microsservissos.product_api.modules.produto.dto;

import br.com.microsservissos.product_api.modules.produto.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesResponse {

    private Integer id;
    private String name;
    @JsonProperty("qualitity_available")
    private Integer qualitityAvailable;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    private SupplierResponse supplier;
    private CategoryResponse category;
    private List<String> sales;


    public static ProductSalesResponse of (Product product, List<String> sales) {
        return ProductSalesResponse.builder()
          .id(product.getId())
          .name(product.getName())
          .qualitityAvailable(product.getQuantityAvailable())
          .createdAt(product.getCreatedAt())
          .supplier(SupplierResponse.of(product.getSupplier()))
          .category(CategoryResponse.of(product.getCategory()))
          .sales(sales)
          .build();
    }

}
