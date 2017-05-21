package com.aleksandrov.controller;

import com.aleksandrov.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;

public class MenuBarController {
	public Main main;
	
	@FXML
	MenuItem aboutItem;
	@FXML
	MenuItem exitItem;
	@FXML
	MenuItem deleteItem;
	@FXML
	MenuItem deleteAllItem;
	
	/*@FXML
	public void handleDeleteAllMenuItem(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Look, a Confirmation Dialog");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			pieChartData.clear();
			barChart.getData().clear();
			tableOfKosts.getItems().clear();
			totalAmountGain = 0;
			totalAmountKost = 0;
			updateLabel();
		} else {
		    alert.close();
		}	
	}*/
	
	@FXML
	public void handleAboutItem(){
		Alert infoWindow = new Alert(AlertType.INFORMATION, "Автор - я, и я пиздат. \nВерсия ПО 1.0.6");
		infoWindow.showAndWait();
	}

	@FXML
	public void handleExitItem(){
		main.primaryStage.close();
	}

	public void setMainApp(Main mainApp) {
		this.main = mainApp;
	}
}
