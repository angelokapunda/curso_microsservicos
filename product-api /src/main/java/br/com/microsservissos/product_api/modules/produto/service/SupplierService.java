package br.com.microsservissos.product_api.modules.produto.service;

import br.com.microsservissos.product_api.config.exception.SuccessResponse;
import br.com.microsservissos.product_api.config.exception.ValidationException;
import br.com.microsservissos.product_api.modules.produto.dto.SupplierRequest;
import br.com.microsservissos.product_api.modules.produto.dto.SupplierResponse;
import br.com.microsservissos.product_api.modules.produto.model.Supplier;
import br.com.microsservissos.product_api.modules.produto.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    public SupplierResponse save (SupplierRequest supplierRequest) {
        validateSupplierNameInformed(supplierRequest);
        var supplier = supplierRepository.save(Supplier.of(supplierRequest));
        return SupplierResponse.of(supplier);
    }

    public SupplierResponse update (SupplierRequest supplierRequest, Integer id) {
        validateSupplierNameInformed(supplierRequest);
        validateInformedId(id);
        var supplier = Supplier.of(supplierRequest);
        supplier.setId(id);
        supplierRepository.save(supplier);
        return SupplierResponse.of(supplier);
    }

    public Supplier findById ( Integer id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given ID"));
    }
    public List<SupplierResponse> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(supplier -> SupplierResponse.of(supplier))
                .toList();
    }
    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(findById(id));
    }

    public List<SupplierResponse> findByName(String name) {
        return supplierRepository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(supplier -> SupplierResponse.of(supplier))
                .toList();
    }

    public void validateSupplierNameInformed(SupplierRequest supplierRequest) {
        if (isEmpty(supplierRequest.getName())) {
            throw new ValidationException("The Supplier was not informed");
        }
    }

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        if (productService.existsBySupplierId(id)) {
            throw new ValidationException("You cannot delete this supplier because it's already defined by a product.");
        }
        supplierRepository.deleteById(id);
        return SuccessResponse.create("The supplier was delete");
    }

    private void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("The supplier Id must be informed");
        }
    }
}
