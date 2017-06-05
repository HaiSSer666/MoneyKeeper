package com.aleksandrov.controller;

import java.time.LocalDate;

/**
 * main GUI controller
 * Author Oleksii A.
 */

import com.aleksandrov.Main;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {	
	//links to sub controllers
	@FXML public MenuViewController menuViewController;
	@FXML public KostsTableViewController kostsTableViewController;
	@FXML public KostsPieChartController kostsPieChartController;
	@FXML public KostsBarChartController kostsBarChartController;
	@FXML public StatusBarController statusBarController;

	//link to main app
	public Main main;

	//variables
	public double totalAmountKost;
	public double totalAmountGain;

	//text fields
	@FXML TextField textFieldSum;
	@FXML TextField textFieldCategory;
	@FXML TextArea textAreaComment;

	//radio buttons
	@FXML RadioButton radioKost;
	@FXML RadioButton radioGain;

	//buttons
	@FXML Button addButton;
	@FXML Button cancelButton;

	//date picker
	@FXML DatePicker datePicker;
	/*--------------------------------------getters-------------------------------------------------------------------------------------*/	
	public double getTotalAmountKost() {
		return totalAmountKost;
	}

	public double getTotalAmountGain() {
		return totalAmountGain;
	}
	/*--------------------------------------Start-up methods----------------------------------------------------------------------------*/		
	public MainController() {
	}

	@FXML
	private void initialize() {	
		//loading sub controllers
		menuViewController.setGuiController(this);
		kostsTableViewController.setGuiController(this);
		kostsPieChartController.setGuiController(this);
		kostsBarChartController.setGuiController(this);
		statusBarController.setGuiController(this);

		//conditions
		enableCancelButton();
		enableAddButton();
		datePicker.setValue(LocalDate.now());
	}

	public void setMainApp(Main mainApp) {
		this.main = mainApp;
	}
	/*--------------------------------------------Buttons-------------------------------------------------------------------------------*/
	@FXML
	public void handleAddButton() {
		try{			
			Kost kost;
			String currentCategory = textFieldCategory.getText();				
			double currentAmount = Double.parseDouble(textFieldSum.getText());
			String currentMonth = String.valueOf(datePicker.getValue().getMonth());
			if (currentAmount<0){
				Alert outputWindow = new Alert(AlertType.WARNING, "Sum must be positive! Please correct your data.");
				outputWindow.showAndWait();
				return;
			}	else {
				if(radioKost.isSelected()){
					kost = new Kost(currentAmount, currentCategory, SpendType.KOST, datePicker.getValue());
					totalAmountKost+=currentAmount;
					kostsPieChartController.updatePieChartData(kost.getCategory(), kost.getAmount());
					kostsBarChartController.getSeriesTotalKosts().getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountKost));
					//adds euro sign to label of pie chart sector (add addEuroSign) TODO
					//Data pieChartSection = new PieChart.Data (kost.getCategory(), kost.getAmount()); 
					//pieChartSection.nameProperty().bind(Bindings.concat(pieChartSection.getName(), " ", pieChartSection.pieValueProperty(), " ˆ"));
				}  
				else {
					kost = new Kost(currentAmount, currentCategory, SpendType.GAIN, datePicker.getValue());
					totalAmountGain+=currentAmount;
					kostsBarChartController.getSeriesTotalGains().getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountGain));
				}
				kost.setComment(textAreaComment.getText());
				kostsTableViewController.kostTableData.add(kost);
				statusBarController.updateLabel(totalAmountKost, totalAmountGain);
				handleCancelButton();	
				kostsBarChartController.getSeriesTotalDifference().getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalAmountGain-totalAmountKost));
			}		
		}

		catch (NumberFormatException e1) {
			Alert outputWindow = new Alert(AlertType.WARNING, "Sum must be a number! Please correct your data.");
			outputWindow.showAndWait();
		}	
	}

	@FXML
	public void handleCancelButton() {
		textFieldCategory.clear();
		textFieldSum.clear();
		textAreaComment.clear();
		radioKost.setSelected(true);
		datePicker.setValue(LocalDate.now());
	}
	/*----------------------------------------Utils-----------------------------------------------------------------------------------------*/	
	public void evaluateTotalAmount(Kost kost) {
		double currentAmount = kost.getAmount();
		if(kost.getSpendType()==SpendType.KOST){
			totalAmountKost=totalAmountKost-currentAmount; 
		}
		else{
			totalAmountGain=totalAmountGain-currentAmount;  
		}
	}

	public void resetTotalAmount() {
		totalAmountGain = 0.0;
		totalAmountKost = 0.0;
	}
	/*----------------------------Buttons enable rules -------------------------------------------------------------------------------------*/
	public void enableAddButton() {
		BooleanBinding bb = new BooleanBinding() {			
			{
				super.bind(textFieldCategory.textProperty(),
						textFieldSum.textProperty()
						);
			}

			@Override
			protected boolean computeValue() {
				return (textFieldCategory.getText().isEmpty()
						|| textFieldSum.getText().isEmpty());
			}
		};
		addButton.disableProperty().bind(bb);
	}	

	public void enableCancelButton() {
		BooleanBinding bb = new BooleanBinding() {			
			{
				super.bind(textFieldCategory.textProperty(),
						textFieldSum.textProperty(),
						textAreaComment.textProperty()
						);
			}

			@Override
			protected boolean computeValue() {
				return (textFieldCategory.getText().isEmpty()
						&& textFieldSum.getText().isEmpty()
						&& textAreaComment.getText().isEmpty());
			}
		};
		cancelButton.disableProperty().bind(bb);
	}
}
