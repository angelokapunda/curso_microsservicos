package br.com.microsservissos.product_api.modules.produto.repository;

import br.com.microsservissos.product_api.modules.produto.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
