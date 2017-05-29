package com.aleksandrov.controller;
/**
 * main GUI controller
 * Author Oleksii A.
 */

import com.aleksandrov.Main;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
//import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GUIController {	
	//links to sub controllers
	@FXML
	public MenuViewController menuViewController;
	@FXML
	public KostsTableViewController kostsTableViewController;
	@FXML
	public KostsPieChartController kostsPieChartController;
	@FXML
	public KostsBarChartController kostsBarChartController;
	
	//link to main app
	public Main main;
	
	//variables
	public double totalAmountKost;
	public double totalAmountGain;
	
	//labels
	@FXML
	Label labelTotalAmountKost;
	@FXML
	Label labelTotalAmountGain;
	@FXML
	Label labelDifference;

	//text fields
	@FXML
	TextField textFieldSum;
	@FXML
	TextField textFieldCategory;
	@FXML
	TextArea textAreaComment;

	//radio buttons
	@FXML
	RadioButton radioKost;
	@FXML
	RadioButton radioGain;

	//buttons
	@FXML
	Button addButton;
	@FXML
	Button cancelButton;
	
	/*--------------------------------------getters for sub controllers-----------------------------------------------------------------*/	
	//temporal solution for barChart
	public double getTotalAmountKost() {
		return totalAmountKost;
	}

	public double getTotalAmountGain() {
		return totalAmountGain;
	}

	public XYChart.Series<String, Double> getSeriesTotalKosts() {
		return kostsBarChartController.getSeriesTotalKosts();
	}

	public XYChart.Series<String, Double> getSeriesTotalGains() {
		return kostsBarChartController.getSeriesTotalGains();
	}

	public XYChart.Series<String, Double> getSeriesTotalDifference() {
		return kostsBarChartController.getSeriesTotalDifference();
	}

	//temporal solution for pieChart
	public ObservableList<PieChart.Data> getPieChartData() {
		return kostsPieChartController.getPieChartData();
	}
	
	/*--------------------------------------Start-up methods----------------------------------------------------------------------------*/		
	public GUIController() {
	}

	@FXML
	private void initialize() {	
		menuViewController.setGuiController(this);
		kostsTableViewController.setGuiController(this);
		kostsPieChartController.setGuiController(this);
		kostsBarChartController.setGuiController(this);

		//conditions
		enableCancelButton();
		enableAddButton();
	}
	
	public void setMainApp(Main mainApp) {
		this.main = mainApp;
	}
	/*--------------------------------------------Buttons-----------------------------------------------------------------------------*/
	@FXML
	public void handleAddButton(){
			try{			
				double currentAmount = Double.parseDouble(textFieldSum.getText());
				String currentCategory = textFieldCategory.getText();
				Kost kost;

				if(radioKost.isSelected()){
					kost = new Kost(currentAmount, currentCategory, SpendType.KOST);
					totalAmountKost+=currentAmount;
					kostsPieChartController.updatePieChartData(kost.getCategory(), kost.getAmount());

					//adds euro sign to label of pie chart sector (add addEuroSign)
					//Data pieChartSection = new PieChart.Data (kost.getCategory(), kost.getAmount()); 
					//pieChartSection.nameProperty().bind(Bindings.concat(pieChartSection.getName(), " ", pieChartSection.pieValueProperty(), " И"));

					//костыль
					@SuppressWarnings("deprecation")
					String currentMonth = kostsBarChartController.getMonthNames().get(kost.getDate().getMonth());//changes
					kostsBarChartController.getSeriesTotalKosts().getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountKost));//changes			
				}  
				else {
					kost = new Kost(currentAmount, currentCategory, SpendType.GAIN);
					totalAmountGain+=currentAmount;

					//костыль
					@SuppressWarnings("deprecation")
					String currentMonth = kostsBarChartController.getMonthNames().get(kost.getDate().getMonth());//changes
					kostsBarChartController.getSeriesTotalGains().getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountGain));//changes
				}
				
				kost.setComment(textAreaComment.getText());
				kostsTableViewController.kostTableData.add(kost);
				updateLabel();
				handleCancelButton();	
				
				@SuppressWarnings("deprecation")
				String currentMonth = kostsBarChartController.getMonthNames().get(kost.getDate().getMonth());//changes
				kostsBarChartController.getSeriesTotalDifference().getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalAmountGain-totalAmountKost));//changes
		}

		catch (NumberFormatException e1){
			Alert outputWindow = new Alert(AlertType.WARNING, "Sum must be a number! Please correct your data.");
			outputWindow.showAndWait();
				}	
		}
	
	@FXML
	public void handleCancelButton(){
		textFieldCategory.clear();
		textFieldSum.clear();
		textAreaComment.clear();
		radioKost.setSelected(true);
	}

/*----------------------------------------Utils-----------------------------------------------------------------------------------*/	
	public void updateLabel(){
		labelTotalAmountGain.setText(Double.toString(totalAmountGain));
		labelTotalAmountKost.setText(Double.toString(totalAmountKost));
		labelDifference.setText(Double.toString(totalAmountGain - totalAmountKost));
	}
	
	public void evaluateTotalAmount(Kost kost){
		double currentAmount = kost.getAmount();
		if(kost.getSpendType()==SpendType.KOST){
			totalAmountKost=totalAmountKost-currentAmount; 
		}
		else{
			totalAmountGain=totalAmountGain-currentAmount;  
		}
	}
	
	public void resetTotalAmount(){
		totalAmountGain = 0.0;
		totalAmountKost = 0.0;
	}
/*----------------------------Buttons enable rules -------------------------------------------------------------------------------*/
	public void enableAddButton(){
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

	public void enableCancelButton(){
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
