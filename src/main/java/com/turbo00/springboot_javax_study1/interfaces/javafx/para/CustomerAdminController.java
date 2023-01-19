package com.turbo00.springboot_javax_study1.interfaces.javafx.para;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

@Component
public class CustomerAdminController implements Initializable {
    @FXML
    TableView tblCustomer;
    @FXML
    Pagination pagi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("gid", "主键");
        columns.put("name", "客户名称");
        columns.put("contact", "联系方式");


    }
}
