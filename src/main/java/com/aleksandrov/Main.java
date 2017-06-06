package com.aleksandrov;

import java.io.IOException;
import com.aleksandrov.controller.*;
import com.aleksandrov.utils.connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	public Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MoneyKeeper");
		this.primaryStage.getIcons().add(new Image("file:src/main/resources/images/piggybank-512.png"));
		GUILoader(); 
		try {
			connection.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void GUILoader() {
		try {
			// Загружаем корневой макет из fxml файла.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GUI.fxml"));//менять на view/GUI.fxml при смене пакета. не затупи, бокопор ебаный
			AnchorPane GUI = (AnchorPane) loader.load();

			// Отображаем сцену
			Scene scene = new Scene(GUI);
			primaryStage.setScene(scene);
			primaryStage.show();
			//primaryStage.setResizable(false);
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