package com.aleksandrov;

import java.io.IOException;
import com.aleksandrov.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	public Stage primaryStage;
	public Scene scene;
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setMaximized(true);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MoneyKeeper");
		this.primaryStage.getIcons().add(new Image("file:src/main/resources/images/piggybank-512.png"));
		GUILoader();  
	}

	public void GUILoader() {
		try {
			// ��������� �������� ����� �� fxml �����.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GUI.fxml"));//������ �� view/GUI.fxml ��� ����� ������. �� ������, ������� ������
			AnchorPane GUI = (AnchorPane) loader.load();

			// ���������� �����
			scene = new Scene(GUI);
			primaryStage.setScene(scene);
			primaryStage.show();
			MainController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}