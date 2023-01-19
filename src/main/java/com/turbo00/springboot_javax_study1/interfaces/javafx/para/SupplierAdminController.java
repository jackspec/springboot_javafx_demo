package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

@Component
public class SupplierAdminController {
    @FXML
    Pagination pagi;
    @FXML
    TableView tblCustomer;
}
