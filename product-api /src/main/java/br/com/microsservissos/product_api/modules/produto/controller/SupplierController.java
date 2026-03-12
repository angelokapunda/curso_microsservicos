package br.com.microsservissos.product_api.modules.produto.controller;

import br.com.microsservissos.product_api.config.exception.SuccessResponse;
import br.com.microsservissos.product_api.modules.produto.dto.CategoryResponse;
import br.com.microsservissos.product_api.modules.produto.dto.SupplierRequest;
import br.com.microsservissos.product_api.modules.produto.dto.SupplierResponse;
import br.com.microsservissos.product_api.modules.produto.service.SupplierService;
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
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse save (@RequestBody SupplierRequest supplierRequest) {
        return supplierService.save(supplierRequest);
    }

    @GetMapping()
    public List<SupplierResponse> findAll() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public SupplierResponse findById(@PathVariable Integer id) {
        return supplierService.findByIdResponse(id);
    }

    @GetMapping("/name/{name}")
    public List<SupplierResponse> findByName(@PathVariable String name) {
        return supplierService.findByName(name);
    }

    @PutMapping("/{id}")
    public SupplierResponse update (@RequestBody SupplierRequest supplierRequest, @PathVariable Integer id) {
        return supplierService.update(supplierRequest, id);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete (@PathVariable Integer id) {
        return supplierService.delete(id);
    }
}
