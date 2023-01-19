package com.turbo00.springboot_javax_study1.interfaces.javafx;

import com.turbo00.springboot_javax_study1.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.turbo00.springboot_javax_study1.SpringbootJavaxStudy1Application.loadFxml;

@Slf4j
@Component
public class LoginController {
    @Value("${app_title}")
    private String appTitle;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MainController mainController;
    @FXML
    public TextField txtUsername;
    @FXML
    public PasswordField txtPassword;

    public void loginButtonClicked() throws Exception {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(loginService.login(username, password)) {
            openMainWindow();
        } else {
            alertLoginFail();
        }
    }

    private void alertLoginFail() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("用户名或密码不正确");
        log.error("用户名或密码不正确");
        alert.showAndWait();
    }

    private void openMainWindow() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = loadFxml("/fxml/main.fxml");
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.setTitle(appTitle);
        primaryStage.show();


        mainController.setPrimaryStage(primaryStage);


        Window window = txtUsername.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).close();
        }
    }
}
