package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import com.turbo00.springboot_javax_study1.domain.para.Customer;
import com.turbo00.springboot_javax_study1.domain.para.CustomerRepository;
import com.turbo00.springboot_javax_study1.interfaces.javafx.Page;
import com.turbo00.springboot_javax_study1.interfaces.javafx.TableWithPaginationAndSorting;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

@Component
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

        //get data
        List<Customer> customerList = getTableData();
        tblCustomer.setItems(FXCollections.observableList(customerList));

        //create page object
        Page<Customer> page = new Page<>(customerList, 2);

        //add pagination into table
        TableWithPaginationAndSorting<Customer> table = new TableWithPaginationAndSorting<>(page, tblCustomer, pagi);

        //global sorting by column age
        Comparator<Customer> asc = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Comparator<Customer> desc = (o1, o2) -> o2.getName().compareTo(o1.getName());
        table.addGlobalOrdering(tblCustomer.getColumns().get(0), asc, desc);

        tblCustomer.setPrefWidth(parentContainer.getScene().getWidth());
        parentContainer.getScene().widthProperty().addListener(
                (observableValue, oldValue, newValue) -> tblCustomer.setPrefWidth(newValue.doubleValue())
        );

        tblCustomer.setPrefHeight(parentContainer.getScene().getHeight());
        parentContainer.getScene().heightProperty().addListener(
                (observableValue, oldValue, newValue) -> tblCustomer.setPrefHeight(newValue.doubleValue())
        );
    }

    private List<Customer> getTableData() {
        return customerRepository.findAll();
    }
}
