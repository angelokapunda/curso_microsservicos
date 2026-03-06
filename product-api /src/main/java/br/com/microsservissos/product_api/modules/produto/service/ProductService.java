package br.com.microsservissos.product_api.modules.produto.service;

import br.com.microsservissos.product_api.config.exception.ValidationException;
import br.com.microsservissos.product_api.modules.produto.dto.ProductRequest;
import br.com.microsservissos.product_api.modules.produto.dto.ProductResponse;
import br.com.microsservissos.product_api.modules.produto.model.Product;
import br.com.microsservissos.product_api.modules.produto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    private static final Integer Zero = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    public ProductResponse save (ProductRequest productRequest) {
        validateProductNameInformed(productRequest);
        validateCategoryAndSupplierIdInformed(productRequest);
        var category = categoryService.findById(productRequest.getCategoryId());
        var supplier = supplierService.findById(productRequest.getSupplierId());
        var product = productRepository.save(Product.of(productRequest, category, supplier));
        return ProductResponse.of(product);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> ProductResponse.of(product))
                .toList();
    }
    public ProductResponse findById(Integer id) {
        return ProductResponse.of(productRepository.findById(id)
                .orElseThrow(() ->
                        new ValidationException("There's no Product for the given ID")));
    }

    public List<ProductResponse> findByName(String name) {
        if (isEmpty(name)) {
            throw new ValidationException("The Product's name was not informed");
        }
        return productRepository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(product -> ProductResponse.of(product))
                .toList();
    }
    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if (isEmpty(categoryId)) {
            throw new ValidationException("The Category Id was not informed");
        }
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(product -> ProductResponse.of(product))
                .toList();
    }

    public List<ProductResponse> findBySupplierId(Integer supplier) {
        if (isEmpty(supplier)) {
            throw new ValidationException("The Supplier Id was not informed");
        }
        return productRepository.findBySupplierId(supplier)
                .stream()
                .map(product -> ProductResponse.of(product))
                .toList();
    }

    public void validateProductNameInformed(ProductRequest productRequest) {
        if (isEmpty(productRequest.getName())) {
            throw new ValidationException("The Product's name was not informed");
        } if (isEmpty(productRequest.getQuantityAvailable())) {
            throw new ValidationException("The quantity's  was not informed");
        } if (productRequest.getQuantityAvailable() <= Zero) {
            throw new ValidationException("The quantity should not be less or equal to Zero");
        }
    }

    public void validateCategoryAndSupplierIdInformed(ProductRequest productRequest) {
        if (isEmpty(productRequest.getCategoryId())) {
            throw new ValidationException("The Category Id was not informed");
        }
        if (isEmpty(productRequest.getSupplierId())) {
            throw new ValidationException("The Supplier Id was not informed");
        }
    }
}
