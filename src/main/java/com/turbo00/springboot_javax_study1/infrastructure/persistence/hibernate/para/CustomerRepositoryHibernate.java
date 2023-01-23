package com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.para;

import com.turbo00.springboot_javax_study1.domain.para.Customer;
import com.turbo00.springboot_javax_study1.domain.para.CustomerRepository;
import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.RepositoryHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("customerRepository")
public class CustomerRepositoryHibernate extends RepositoryHibernate<Customer, Long> implements CustomerRepository {
    @Autowired
    DataSource dataSource;

    public CustomerRepositoryHibernate() {
        super(Customer.class);
    }
}
