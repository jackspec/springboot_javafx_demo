package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import com.turbo00.springboot_javax_study1.application.para.CustomerService;
import com.turbo00.springboot_javax_study1.domain.para.Customer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;


@Controller
public class CustomerDialogController implements Initializable {
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtContact;

    @Autowired
    CustomerService customerService;

    @Setter
    CustomerAdminController parentController;

    SimpleObjectProperty<Customer> currCustomerProperty;

    public CustomerDialogController() {
        currCustomerProperty = new SimpleObjectProperty<>(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currCustomerProperty.addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer oldValue, Customer newValue) {
                if (newValue != null) {
                    txtName.setText(newValue.getName());
                    txtContact.setText(newValue.getContact());
                }
            }
        });
    }

    public void setCustomer(Customer customer) {
        currCustomerProperty.setValue(customer);
    }

    public void btnOkClicked(ActionEvent actionEvent) {
        String strName = txtName.getText();
        String strContact = txtContact.getText();

        if(StringUtils.isEmpty(strName)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("客户名称不能为空");
            alert.showAndWait();
            return;
        }

        Customer customer;
        if (currCustomerProperty.getValue() != null) {
            //modify
            customer = currCustomerProperty.getValue();
        } else {
            //new
            customer = new Customer();
        }
        customer.setName(strName);
        customer.setContact(strContact);

        if (currCustomerProperty.getValue() != null) {
            //modify
            customerService.updateCustomer(customer);
        } else {
            //add
            customerService.addCustomer(customer);
        }
        currCustomerProperty.setValue(null);
        Window window = txtName.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }

        parentController.refresh();
    }

    public void btnCancelClicked(ActionEvent actionEvent) {
        Window window = txtName.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }
    }
}
