package br.com.microsservissos.product_api.modules.produto.service;

import br.com.microsservissos.product_api.config.exception.SuccessResponse;
import br.com.microsservissos.product_api.config.exception.ValidationException;
import br.com.microsservissos.product_api.modules.produto.dto.ProductCheckStockRequest;
import br.com.microsservissos.product_api.modules.produto.dto.ProductQuantityDTO;
import br.com.microsservissos.product_api.modules.produto.dto.ProductRequest;
import br.com.microsservissos.product_api.modules.produto.dto.ProductResponse;
import br.com.microsservissos.product_api.modules.produto.dto.ProductSalesResponse;
import br.com.microsservissos.product_api.modules.produto.dto.ProductStockDTO;
import br.com.microsservissos.product_api.modules.produto.model.Product;
import br.com.microsservissos.product_api.modules.produto.repository.ProductRepository;
import br.com.microsservissos.product_api.modules.sales.client.SalesClient;
import br.com.microsservissos.product_api.modules.sales.dto.SalesConfirmationDTO;
import br.com.microsservissos.product_api.modules.sales.enums.SalesStatus;
import br.com.microsservissos.product_api.modules.sales.rabbitmq.SalesConfirmationSender;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ProductService {

    private static final Integer Zero = 0;

    @Autowired
    private ProductRepository productRepository;

    @Lazy
    @Autowired
    private SupplierService supplierService;

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    @Autowired
    private SalesClient salesClient;

    public ProductResponse save (ProductRequest productRequest) {
        validateProductNameInformed(productRequest);
        validateCategoryAndSupplierIdInformed(productRequest);
        var category = categoryService.findById(productRequest.getCategoryId());
        var supplier = supplierService.findById(productRequest.getSupplierId());
        var product = productRepository.save(Product.of(productRequest, category, supplier));
        return ProductResponse.of(product);
    }

    public ProductResponse update (ProductRequest productRequest, Integer id) {
        validateProductNameInformed(productRequest);
        validateInformedId(id);
        validateCategoryAndSupplierIdInformed(productRequest);
        var category = categoryService.findById(productRequest.getCategoryId());
        var supplier = supplierService.findById(productRequest.getSupplierId());
        var product = Product.of(productRequest, category, supplier);
        product.setId(id);
        productRepository.save(product);
        return ProductResponse.of(product);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> ProductResponse.of(product))
                .toList();
    }
    public Product findById(Integer id) {
        validateInformedId(id);
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no product for the given ID."));
    }

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
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

    public Boolean existsByCategoryId (Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId (Integer supplierId) {
        return productRepository.existsBySupplierId(supplierId);
    }

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        productRepository.deleteById(id);
        return SuccessResponse.create("The Product was delete");
    }

    private void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("The supplier Id must be informed");
        }
    }

    public void updateProductStock(ProductStockDTO productStockDTO) {
        try {
            validateStockUpdateData(productStockDTO);
            updateStock(productStockDTO);

        } catch (Exception ex) {
            log.error("Error while trying to update stock for messagee with error: {}", ex.getMessage(), ex);
            var rejectMessage = new SalesConfirmationDTO(productStockDTO.getSalesId(), SalesStatus.REJECTED);
            salesConfirmationSender.sendSalesConfirmationMesseg(rejectMessage);
        }
    }
    @Transactional
    private void updateStock(ProductStockDTO productStockDTO) {
        var productForUpdate = new ArrayList<Product>();
        productStockDTO.getProducts()
            .forEach(salesProduct -> {
                var existingProduct = findById(salesProduct.getProductId());
                if (salesProduct.getQuantity() > existingProduct.getQuantityAvailable()) {
                    throw new ValidationException(
                            String.format("The product %S is out of stock", existingProduct.getId())
                    );
                }
                existingProduct.updateStock(salesProduct.getQuantity());
                productForUpdate.add(existingProduct);
            });
        if (!isEmpty(productForUpdate)) {
            productRepository.saveAll(productForUpdate);
            var approvedMesssege = new SalesConfirmationDTO(productStockDTO.getSalesId(), SalesStatus.APPROVED);
            salesConfirmationSender.sendSalesConfirmationMesseg(approvedMesssege);
        }

    }
    @Transactional
    private void validateStockUpdateData(ProductStockDTO product) {
        if (isEmpty(product) || isEmpty(product.getSalesId())) {
            throw new ValidationException("The product data and sales ID must be informed");
        }
        if (isEmpty(product.getProducts())) {
            throw new ValidationException("The sales products must be informed");
        }
        product.getProducts()
                .forEach(salesProduct -> {
                    if (isEmpty(salesProduct.getQuantity()) || isEmpty(salesProduct.getProductId())) {
                        throw new ValidationException("The product ID and the quantity must be informed");
                    }
                });
    }

    public ProductSalesResponse findProductSales(Integer id) {
        var product =  findById(id);
        try {
            var sales = salesClient.findSalesByProductId(product.getId())
                    .orElseThrow(() -> new ValidationException("The sales was not found by this product"));
            return ProductSalesResponse.of(product, sales.getSalesIds());
        } catch (Exception ex) {
            throw new ValidationException("There was an error trying to get productś sales");
        }
    }

    public SuccessResponse checkProductsStock(ProductCheckStockRequest request) {
        if (isEmpty(request) || isEmpty(request.getProducts())) {
            throw new ValidationException("The request data and products must be informed");
        }
        request.getProducts()
                .forEach(this::validateStock);
        return SuccessResponse.create("The Stock is ok!");
    }

    private void validateStock(ProductQuantityDTO productQuantityDTO) {
        if (isEmpty(productQuantityDTO.getProductId()) || isEmpty(productQuantityDTO.getQuantity())) {
            throw new ValidationException("Product ID and quantity must be informad");
        }
        var product = findById(productQuantityDTO.getProductId());
        if (productQuantityDTO.getQuantity() > product.getQuantityAvailable()) {
            throw new ValidationException(String.format("The product $s is out of stock.", product.getId()));
        }
    }
}
