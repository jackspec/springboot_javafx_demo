package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import com.turbo00.springboot_javax_study1.application.para.CustomerService;
import com.turbo00.springboot_javax_study1.domain.para.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDialogController {
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtContact;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerAdminController customerAdminController;

    public void btnOkClicked(ActionEvent actionEvent) {
        String strName = txtName.getText();
        String strContact = txtContact.getText();

        if(StringUtils.isEmpty(strName)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("客户名称不能为空");
            alert.showAndWait();
            return;
        }

        Customer customer = new Customer();
        customer.setName(strName);
        customer.setContact(strContact);

        customerService.addCustomer(customer);
        Window window = txtName.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }

        customerAdminController.refresh();
    }

    public void btnCancelClicked(ActionEvent actionEvent) {
        Window window = txtName.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }
    }
}
