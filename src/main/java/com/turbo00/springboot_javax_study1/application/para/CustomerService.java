package com.turbo00.springboot_javax_study1.application.para;

import com.turbo00.springboot_javax_study1.domain.para.Customer;
import com.turbo00.springboot_javax_study1.domain.para.CustomerRepository;
import com.turbo00.springboot_javax_study1.domain.service.LogDesc;
import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.JpaCriteriaHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("customerService")
public class CustomerService {
    @Inject
    private CustomerRepository customerRepository;

    @LogDesc(action = "add_customer")
    public Customer addCustomer(Customer customer) {
        customer = customerRepository.store(customer);
        return customer;
    }

    @LogDesc(action = "update_customer")
    public void updateCustomer(Customer customer) {
        customerRepository.store(customer);
    }

    @LogDesc(action = "delete_customer")
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    @LogDesc(action = "view_customer")
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id);
    }

    @LogDesc(action = "view_customer")
    public Long customerCount(JpaCriteriaHolder jpaCriteriaHolder) {
        return customerRepository.count(jpaCriteriaHolder);
    }

    @LogDesc(action = "view_customer")
    public List<Customer> listCustomer(int itemStartNumber, int pageSize, JpaCriteriaHolder jpaCriteriaHolder) {
        return customerRepository.listPart(itemStartNumber, pageSize, jpaCriteriaHolder);
    }
}
