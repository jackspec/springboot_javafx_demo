package com.turbo00.springboot_javax_study1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class SpringbootJavaxStudy1Application extends Application {

	private static ApplicationContext applicationContext;

	public static FXMLLoader loadFxml(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(SpringbootJavaxStudy1Application.class.getResource(fxmlPath));
		fxmlLoader.setControllerFactory(applicationContext::getBean);
		return fxmlLoader;
	}


	public static void main(String[] args) {
		applicationContext = SpringApplication.run(SpringbootJavaxStudy1Application.class, args);
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(loadFxml("/fxml/login.fxml").load()));
		primaryStage.setTitle("登录");
		primaryStage.show();
	}
}
