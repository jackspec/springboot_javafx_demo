package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import com.turbo00.springboot_javax_study1.domain.para.Customer;
import com.turbo00.springboot_javax_study1.domain.para.CustomerRepository;
import com.turbo00.springboot_javax_study1.infrastructure.persistence.hibernate.JpaCriteriaHolder;
import com.turbo00.springboot_javax_study1.interfaces.javafx.Page;
import com.turbo00.springboot_javax_study1.interfaces.javafx.TableWithPaginationAndSorting;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class CustomerAdminController implements Initializable {
    @Setter
    BorderPane parentContainer;

    @FXML
    Pagination pagi;
    @FXML
    TableView<Customer> tblCustomer;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Customer, String> nameCol = new TableColumn<>("姓名");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Customer, String> contactCol = new TableColumn<>("联系方式");
        contactCol.setCellValueFactory(new PropertyValueFactory("contact"));
        tblCustomer.getColumns().addAll(nameCol, contactCol);
        tblCustomer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //create page object
        CustomerPage customerPage = new CustomerPage(getJpaCriteriaHolder(false), getJpaCriteriaHolder(true), 5);

        //get first page data
        tblCustomer.setItems(FXCollections.observableList(customerPage.listPartRow(customerPage.getPageSize(), 0)));

        //add pagination into table
        TableWithPaginationAndSorting<Customer> table = new TableWithPaginationAndSorting<>(customerPage, tblCustomer, pagi);

        //global sorting by column name
        Comparator<Customer> asc = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Comparator<Customer> desc = (o1, o2) -> o2.getName().compareTo(o1.getName());
        table.addGlobalOrdering(tblCustomer.getColumns().get(1), asc, desc);

        tblCustomer.setPrefWidth(parentContainer.getScene().getWidth());
        parentContainer.getScene().widthProperty().addListener(
                (observableValue, oldValue, newValue) -> tblCustomer.setPrefWidth(newValue.doubleValue())
        );

        tblCustomer.setPrefHeight(parentContainer.getScene().getHeight());
        parentContainer.getScene().heightProperty().addListener(
                (observableValue, oldValue, newValue) -> tblCustomer.setPrefHeight(newValue.doubleValue())
        );
    }


    public JpaCriteriaHolder getJpaCriteriaHolder(boolean isCount) {
        Session session = customerRepository.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq;
        if (isCount) {
            cq = cb.createQuery(Long.class);
        } else {
            cq = cb.createQuery(Customer.class);
        }
        Root<Customer> root = cq.from(Customer.class);

        JpaCriteriaHolder jpaCriteriaHolder = new JpaCriteriaHolder();
        jpaCriteriaHolder.setSession(session);
        jpaCriteriaHolder.setCriteriaBuilder(cb);
        jpaCriteriaHolder.setCriteriaQuery(cq);
        jpaCriteriaHolder.setRoot(root);

        return jpaCriteriaHolder;
    }
}
