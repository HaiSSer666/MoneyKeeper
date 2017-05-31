package com.aleksandrov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatusBarController {
	public GUIController guiController;
	
	//labels
	@FXML
	Label labelTotalAmountKost;
	@FXML
	Label labelTotalAmountGain;
	@FXML
	Label labelDifference;
	
	public void updateLabel(double totalAmountKost, double totalAmountGain){
		labelTotalAmountGain.setText(Double.toString(totalAmountGain));
		labelTotalAmountKost.setText(Double.toString(totalAmountKost));
		labelDifference.setText(Double.toString(totalAmountGain - totalAmountKost));
	}
	
	//link to main controller
		public void setGuiController(GUIController guiController) {
			this.guiController = guiController;
		}

}
