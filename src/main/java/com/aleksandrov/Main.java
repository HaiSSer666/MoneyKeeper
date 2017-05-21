package com.aleksandrov;

import java.io.IOException;

import com.aleksandrov.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;

public class Main extends Application {
	public Stage primaryStage;
	//private BorderPane MenuView;
	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MoneyKeeper");
        this.primaryStage.getIcons().add(new Image("file:src/main/resources/images/piggybank-512.png"));
        //MenuLoader();
        GUILoader();
        
    }
	
	/*public void MenuLoader() {
        try {
        	// ��������� �������� ����� �� fxml �����.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("controller/Test.fxml"));//������ �� view/GUI.fxml ��� ����� ������. �� ������, ������� ������
            MenuView = (BorderPane) loader.load();
            
            // ���������� �����
            Scene scene = new Scene(MenuView);
            primaryStage.setScene(scene);
            //primaryStage.setResizable(false);
            primaryStage.show();
            
            //MenuBarController controller = loader.getController();
            //controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
	
	public void GUILoader() {
        try {
        	// ��������� �������� ����� �� fxml �����.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GUI.fxml"));//������ �� view/GUI.fxml ��� ����� ������. �� ������, ������� ������
            AnchorPane GUI = (AnchorPane) loader.load();
            
            //test
            //MenuView.setTop(GUI);
            
            // ���������� �����
            Scene scene = new Scene(GUI);
            primaryStage.setScene(scene);
            primaryStage.show();
            GUIController controller = loader.getController();
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