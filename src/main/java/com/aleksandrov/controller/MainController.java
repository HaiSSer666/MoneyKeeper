package com.aleksandrov.controller;

/**
 * main GUI controller
 * Author Oleksii A.
 */

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import com.aleksandrov.Main;
import com.aleksandrov.dao.KostDao;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {	
	@FXML ComboBox<String> comboBoxCategory;
	@FXML ComboBox<String> comboBoxMonths;
	@FXML ComboBox<Integer> comboBoxYears;
	HashSet<String> listOfKosts = new HashSet<String>();
	ObservableList<Integer> listOfYears = FXCollections.observableArrayList(
		        2017,
		        2018,
		        2019
		    );
	@FXML TextField textFieldResult;
	@FXML Button searchButton;
	
	@FXML
	public void handleSearchButton() {
		try {
			double monthlyTotalCategoryAmount = kostDao.getTotalCategoryAmount(comboBoxCategory.getValue());
			System.out.println(monthlyTotalCategoryAmount);
			textFieldResult.setText(String.valueOf(monthlyTotalCategoryAmount)+" Euro");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
		listOfKosts = kostDao.getListOfPieChartKosts();
		comboBoxCategory.getItems().addAll(listOfKosts);
		comboBoxMonths.getItems().addAll(kostsBarChartController.getMonthsOfYear());
		comboBoxYears.getItems().addAll(listOfYears);
		
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
			Month month = datePicker.getValue().getMonth();//test
			double totalMonthlyKost = kostDao.getTotalAmount(SpendType.KOST, month);
			double totalMonthlyGain = kostDao.getTotalAmount(SpendType.GAIN, month);
			
			if (currentAmount<0){
				Alert outputWindow = new Alert(AlertType.WARNING, "Sum must be positive! Please correct your data.");
				outputWindow.showAndWait();
				return;
			}	else {
				if(radioKost.isSelected()){
					kost = new Kost(currentAmount, currentCategory, SpendType.KOST, datePicker.getValue(), textAreaComment.getText());
					totalMonthlyKost += currentAmount;//test
					kostsPieChartController.updatePieChartData(kost.getCategory(), kost.getAmount());
					kostsBarChartController.getSeriesTotalKosts().getData().add(new XYChart.Data<String, Double>(currentMonth, totalMonthlyKost));
					//adds euro sign to label of pie chart sector (add addEuroSign) TODO
					//Data pieChartSection = new PieChart.Data (kost.getCategory(), kost.getAmount()); 
					//pieChartSection.nameProperty().bind(Bindings.concat(pieChartSection.getName(), " ", pieChartSection.pieValueProperty(), " ˆ"));
				}  
				else {
					kost = new Kost(currentAmount, currentCategory, SpendType.GAIN, datePicker.getValue(), textAreaComment.getText());
					totalMonthlyGain += currentAmount;//test
					kostsBarChartController.getSeriesTotalGains().getData().add(new XYChart.Data<String, Double>(currentMonth, totalMonthlyGain));
				}
				try {
					kostDao.insertKost(kost);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				kostsTableViewController.kostTableData.add(kost);
				double totalAmountKost = kostDao.getTotalAmount(SpendType.KOST);
				double totalAmountGain = kostDao.getTotalAmount(SpendType.GAIN);
				statusBarController.updateLabel(totalAmountKost, totalAmountGain);
				handleCancelButton();	
				kostsBarChartController.getSeriesTotalDifference().getData().
				add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalMonthlyGain-totalMonthlyKost));//test
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