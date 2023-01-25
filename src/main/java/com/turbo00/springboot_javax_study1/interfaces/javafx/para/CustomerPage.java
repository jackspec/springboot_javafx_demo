package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import com.turbo00.springboot_javax_study1.SpringbootJavaxStudy1Application;
import com.turbo00.springboot_javax_study1.application.para.CustomerService;
import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.JpaCriteriaHolder;
import com.turbo00.springboot_javax_study1.interfaces.javafx.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerPage extends Page {
    /**
     * @param jpaCriteriaHolder
     * @param jpaCriteriaHolderCount
     * @param pageSize               the number of data in per page
     */
    public CustomerPage(JpaCriteriaHolder jpaCriteriaHolder, JpaCriteriaHolder jpaCriteriaHolderCount, int pageSize) {
        super(jpaCriteriaHolder, jpaCriteriaHolderCount, pageSize);
    }

    @Override
    protected int rowCount() {
        CustomerService customerService = (CustomerService) SpringbootJavaxStudy1Application.getApplicationContext().getBean("customerService");
        return customerService.customerCount(jpaCriteriaHolderCount).intValue();
    }

    @Override
    protected List listPartRow(int itemStartNumber, int pageSize) {
        CustomerService customerService = (CustomerService) SpringbootJavaxStudy1Application.getApplicationContext().getBean("customerService");
        return customerService.listCustomer(itemStartNumber, pageSize, jpaCriteriaHolder);
    }
}
