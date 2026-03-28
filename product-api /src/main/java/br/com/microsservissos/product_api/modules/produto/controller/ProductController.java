package br.com.microsservissos.product_api.modules.produto.controller;

import br.com.microsservissos.product_api.config.exception.SuccessResponse;
import br.com.microsservissos.product_api.modules.produto.dto.CategoryRequest;
import br.com.microsservissos.product_api.modules.produto.dto.CategoryResponse;
import br.com.microsservissos.product_api.modules.produto.dto.ProductCheckStockRequest;
import br.com.microsservissos.product_api.modules.produto.dto.ProductRequest;
import br.com.microsservissos.product_api.modules.produto.dto.ProductResponse;
import br.com.microsservissos.product_api.modules.produto.dto.ProductSalesResponse;
import br.com.microsservissos.product_api.modules.produto.service.ProductService;
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
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest productRequest) {
        return productService.save(productRequest);
    }

    @PostMapping("/check-stock")
    public SuccessResponse checkProductStock(@RequestBody ProductCheckStockRequest request) {
        return productService.checkProductsStock(request);
    }

    @GetMapping()
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Integer id) {
        return productService.findByIdResponse(id);
    }

    @GetMapping("/name/{name}")
    public List<ProductResponse> findByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer supplierId) {
        return productService.findBySupplierId(supplierId);
    }

    @GetMapping("{id}/sales")
    public ProductSalesResponse findProductSales(@PathVariable Integer id) {
        return productService.findProductSales(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update (@RequestBody ProductRequest productRequest, @PathVariable Integer id) {
        return productService.update(productRequest, id);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete (@PathVariable Integer id) {
        return productService.delete(id);
    }
}
