package br.com.microsservissos.product_api.modules.produto.model;

import br.com.microsservissos.product_api.modules.produto.dto.ProductRequest;
import br.com.microsservissos.product_api.modules.produto.dto.ProductResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY", nullable = false)
    private Category category;

    @Column(name = "QUANTITY_AVAILABLE")
    private Integer quantityAvailable;

    @Column(name = "CREATED_AT" ,nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public static Product of (ProductRequest productRequest, Category category, Supplier supplier) {
        var product = new Product();
        product.setName(productRequest.getName());
        product.setQuantityAvailable(productRequest.getQuantityAvailable());
        product.setCategory(category);
        product.setSupplier(supplier);
        return product;
    }

    public void updateStock(Integer quantity) {
        quantityAvailable -= quantity;
    }

}
