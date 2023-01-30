package com.turbo00.springboot_javax_study1.application.para;

import com.turbo00.springboot_javax_study1.domain.para.Supplier;
import com.turbo00.springboot_javax_study1.domain.para.SupplierRepository;
import com.turbo00.springboot_javax_study1.domain.service.LogDesc;
import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.JpaCriteriaHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("supplierService")
public class SupplierService {
    @Inject
    private SupplierRepository supplierRepository;

    @LogDesc(action = "add_supplier")
    public Supplier addSupplier(Supplier supplier) {
        supplier = supplierRepository.store(supplier);
        return supplier;
    }

    @LogDesc(action = "update_supplier")
    public void updateSupplier(Supplier supplier) {
        supplierRepository.store(supplier);
    }

    @LogDesc(action = "delete_supplier")
    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);
    }

    @LogDesc(action = "view_supplier")
    public Supplier getSupplier(Long id) {
        return supplierRepository.findById(id);
    }

    @LogDesc(action = "view_supplier")
    public Long supplierCount(JpaCriteriaHolder jpaCriteriaHolder) {
        return supplierRepository.count(jpaCriteriaHolder);
    }

    @LogDesc(action = "view_supplier")
    public List<Supplier> listSupplier(int itemStartNumber, int pageSize, JpaCriteriaHolder jpaCriteriaHolder) {
        return supplierRepository.listPart(itemStartNumber, pageSize, jpaCriteriaHolder);
    }
}
