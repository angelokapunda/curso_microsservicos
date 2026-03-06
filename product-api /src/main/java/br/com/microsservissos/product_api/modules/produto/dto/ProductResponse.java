package br.com.microsservissos.product_api.modules.produto.dto;

import br.com.microsservissos.product_api.modules.produto.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    @JsonProperty("qualitity_available")
    private Integer qualitityAvailable;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    private SupplierResponse supplier;
    private CategoryResponse category;

    public static ProductResponse of (Product product) {
      ProductResponse productResponse = new ProductResponse();
      productResponse.setId(product.getId());
      productResponse.setName(product.getNome());
      productResponse.setQualitityAvailable(product.getQuantityAvailable());
      productResponse.setCreatedAt(product.getCreatedAt());
      productResponse.setSupplier(SupplierResponse.of(product.getSupplier()));
      productResponse.setCategory(CategoryResponse.of(product.getCategory()));
      return productResponse;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplierResponse getSupplier() {
        return supplier;
    }

    public Integer getQualitityAvailable() {
        return qualitityAvailable;
    }

    public void setQualitityAvailable(Integer qualitityAvailable) {
        this.qualitityAvailable = qualitityAvailable;
    }

    public void setSupplier(SupplierResponse supplier) {
        this.supplier = supplier;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
