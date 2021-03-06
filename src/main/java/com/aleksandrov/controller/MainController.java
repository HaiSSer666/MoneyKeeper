package com.aleksandrov.controller;

/**
 * main GUI controller
 * Author Oleksii A.
 */

import java.sql.SQLException;
import java.time.LocalDate;
import com.aleksandrov.Main;
import com.aleksandrov.dao.KostDao;
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
	//link to dao
	KostDao kostDao = new KostDao();

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
	@FXML
	public DatePicker datePicker;
	/*--------------------------------------Start-up methods----------------------------------------------------------------------------*/		
	public MainController() {
	}

	@FXML
	private void initialize() {	
		kostDao.getAllKosts().forEach(kost -> {
			kost.getCategory();
		});
		
		//restoting data with dao
		totalAmountKost = kostDao.getTotalAmount(SpendType.KOST);
		totalAmountGain = kostDao.getTotalAmount(SpendType.GAIN);
		statusBarController.updateLabel(totalAmountKost, totalAmountGain);
		
		//loading sub controllers
		menuViewController.setGuiController(this);
		kostsTableViewController.setGuiController(this);
		kostsPieChartController.setGuiController(this);
		kostsBarChartController.setGuiController(this);
		statusBarController.setGuiController(this);

		//enable conditions
		enableCancelButton();
		enableAddButton();
		datePicker.setValue(LocalDate.now());
		
		//kostDao.getListOfPieChartKosts().forEach(kost -> System.out.println(kost));	
		//System.out.println(kostDao.getListOfPieChartKosts());
		/*kostsPieChartController.getPieChartData().forEach(data -> {
			System.out.println(data.getName());
			System.out.println(data.getPieValue());
		});*/
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
			String currentMonth = datePicker.getValue().getMonth().toString();

			double totalAmountKost = kostDao.getTotalAmount(SpendType.KOST);
			double totalAmountGain = kostDao.getTotalAmount(SpendType.GAIN);

			if (currentAmount<0){
				Alert outputWindow = new Alert(AlertType.WARNING, "Sum must be positive! Please correct your data.");
				outputWindow.showAndWait();
				return;
			}	else {
				if(radioKost.isSelected()){
					kost = new Kost(currentAmount, currentCategory, SpendType.KOST, datePicker.getValue(), textAreaComment.getText());
					totalAmountKost += currentAmount;
					kostsPieChartController.updatePieChartData(kost.getCategory(), kost.getAmount());
					kostsBarChartController.getSeriesTotalKosts().getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountKost));
					//adds euro sign to label of pie chart sector (add addEuroSign) TODO
					//Data pieChartSection = new PieChart.Data (kost.getCategory(), kost.getAmount()); 
					//pieChartSection.nameProperty().bind(Bindings.concat(pieChartSection.getName(), " ", pieChartSection.pieValueProperty(), " �"));
				}  
				else {
					kost = new Kost(currentAmount, currentCategory, SpendType.GAIN, datePicker.getValue(), textAreaComment.getText());
					totalAmountGain += currentAmount;
					kostsBarChartController.getSeriesTotalGains().getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountGain));
				}
				kostsTableViewController.kostTableData.add(kost);
				statusBarController.updateLabel(totalAmountKost, totalAmountGain);
				handleCancelButton();	
				kostsBarChartController.getSeriesTotalDifference().getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalAmountGain-totalAmountKost));
				try {
					kostDao.insertKost(kost);
				} catch (SQLException e) {
					e.printStackTrace();
				}

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
	/*----------------------------Buttons enable rules -------------------------------------------------------------------------------------*/
	public void enableAddButton() {
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
						|| textFieldSum.getText().isEmpty()
						|| textAreaComment.getText().isEmpty());
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