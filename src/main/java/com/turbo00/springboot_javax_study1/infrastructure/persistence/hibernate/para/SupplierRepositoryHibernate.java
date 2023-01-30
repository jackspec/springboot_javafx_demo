package com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.para;

import com.turbo00.springboot_javax_study1.domain.para.Customer;
import com.turbo00.springboot_javax_study1.domain.para.CustomerRepository;
import com.turbo00.springboot_javax_study1.domain.para.Supplier;
import com.turbo00.springboot_javax_study1.domain.para.SupplierRepository;
import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.RepositoryHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("supplierRepository")
public class SupplierRepositoryHibernate extends RepositoryHibernate<Supplier, Long> implements SupplierRepository {
    @Autowired
    DataSource dataSource;

    public SupplierRepositoryHibernate() {
        super(Supplier.class);
    }
}
