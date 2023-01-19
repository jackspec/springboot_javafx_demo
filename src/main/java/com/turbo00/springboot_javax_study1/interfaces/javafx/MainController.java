package com.turbo00.springboot_javax_study1.interfaces.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.turbo00.springboot_javax_study1.SpringbootJavaxStudy1Application.loadFxml;

@Component
public class MainController {
    @Setter
    Stage primaryStage;

    @FXML
    private Pane container;

    @Value("${app_title}")
    private String appTitle;

    public void menuCustomerAdminClicked(ActionEvent actionEvent) throws IOException {
        Parent cusAdminWnd = loadFxml("/fxml/para/customerAdmin.fxml").load();
        container.getChildren().clear();
        container.getChildren().add(cusAdminWnd);

        primaryStage.setTitle(appTitle + ":客户信息管理");
    }

    public void menuSupplierAdminClicked(ActionEvent actionEvent) throws IOException {
        Parent supAdminWnd = loadFxml("/fxml/para/supplierAdmin.fxml").load();
        container.getChildren().clear();
        container.getChildren().add(supAdminWnd);

        primaryStage.setTitle(appTitle + ":供应商信息管理");
    }
}
