package br.com.microsservissos.product_api.modules.produto.controller;

import br.com.microsservissos.product_api.modules.produto.dto.SupplierRequest;
import br.com.microsservissos.product_api.modules.produto.dto.SupplierResponse;
import br.com.microsservissos.product_api.modules.produto.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse save (@RequestBody SupplierRequest supplierRequest) {
        return supplierService.save(supplierRequest);
    }
}
