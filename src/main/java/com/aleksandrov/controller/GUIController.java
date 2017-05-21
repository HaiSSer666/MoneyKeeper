package com.aleksandrov.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import com.aleksandrov.Main;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
//import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GUIController {
	@FXML
	Button testButton;
	@FXML
	public void testButtonHandler() throws InterruptedException{
		Thread.sleep(3000);
		textAreaComment.setText("***");
	}
	
	
	//variables
	public double totalAmountKost;
	public double totalAmountGain;
	public Main main;
	
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

	//menu items
	@FXML
	MenuItem aboutItem;
	@FXML
	MenuItem exitItem;
	@FXML
	MenuItem deleteItem;
	@FXML
	MenuItem deleteAllItem;
	
	//charts and axis
	@FXML
	private PieChart pieChart;	
	@FXML
	private BarChart<String, Double> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;

	XYChart.Series<String, Double> seriesTotalKosts = new XYChart.Series<>();
	XYChart.Series<String, Double> seriesTotalGains = new XYChart.Series<>();
	XYChart.Series<String, Double> seriesTotalDifference = new XYChart.Series<>();

	//lists
	private ObservableList<String> monthNames = FXCollections.observableArrayList();	
	private ObservableList<Kost> kostTableData = FXCollections.observableArrayList();
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	ArrayList<Kost> listOfKosts = new ArrayList<>();
	ArrayList<Kost> listOfGains = new ArrayList<>();

	//table columns
	@FXML
	public TableView<Kost> tableOfKosts;
	@FXML
	TableColumn<Kost, Double> columnAmount;
	@FXML
	TableColumn<Kost, String> columnCategory;
	@FXML
	TableColumn<Kost, SpendType> columnType;
	@FXML
	TableColumn<Kost, Date> columnDate; //LocalDate -> Date
	@FXML
	TableColumn<Kost, String> columnComment;

	/*--------------------------------------Start-up methods----------------------------------------------------------------------------*/		
	public GUIController() {
	}

	/*is used after fxml-file loading*/
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {	
		columnAmount.setCellValueFactory(new PropertyValueFactory<Kost, Double>("amount"));	
		columnCategory.setCellValueFactory(new PropertyValueFactory<Kost, String>("category"));		
		columnType.setCellValueFactory(new PropertyValueFactory<Kost, SpendType>("spendType"));	
		columnDate.setCellValueFactory(new PropertyValueFactory<Kost, Date>("date"));	//LocalDate -> Date, dateOfPurchaseIncome -> date
		columnComment.setCellValueFactory(new PropertyValueFactory<Kost, String>("comment"));
		tableOfKosts.setItems(kostTableData);

		pieChart.setData(pieChartData);
		pieChart.setLegendVisible(false);
		pieChart.setTitle("Distribution of kosts");		
		pieChart.setLegendSide(Side.BOTTOM);

		barChart.setTitle("Chart of total balance");
		barChart.getData().addAll(seriesTotalKosts, seriesTotalGains, seriesTotalDifference);
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		monthNames.addAll(Arrays.asList(months));
		xAxis.setCategories(monthNames);     

		//отображает только тотал кост/гейн, а не по мес€цам
		seriesTotalKosts.setName("Total kosts");
		seriesTotalGains.setName("Total gains");;
		seriesTotalDifference.setName("Difference");;

		//conditions
		enableCancelButton();
		enableAddButton();
		
		
		//test
		
	}
	
	public void setMainApp(Main mainApp) {
		this.main = mainApp;
	}
	/*--------------------------------------------Add button-----------------------------------------------------------------------------*/
	@FXML
	public void handleAddButton(){
		/*if(Integer.parseInt(textFieldSum.getText())<0){
			Alert outputWindow = new Alert(AlertType.WARNING, "Sum must be greater than 0! Please correct your data.");
			outputWindow.showAndWait();
		}
		else{
			
		}*/
			try{			
				double currentAmount = Double.parseDouble(textFieldSum.getText());
				String currentCategory = textFieldCategory.getText();
				Kost kost;

				if(radioKost.isSelected()){
					kost = new Kost(currentAmount, currentCategory, SpendType.KOST);
					listOfKosts.add(kost);
					totalAmountKost+=currentAmount;
					updatePieChartData(kost.getCategory(), kost.getAmount());

					//adds euro sign to label of pie chart sector (add addEuroSign)
					//Data pieChartSection = new PieChart.Data (kost.getCategory(), kost.getAmount()); 
					//pieChartSection.nameProperty().bind(Bindings.concat(pieChartSection.getName(), " ", pieChartSection.pieValueProperty(), " И"));

					//костыль
					@SuppressWarnings("deprecation")
					String currentMonth = monthNames.get(kost.getDate().getMonth());
					seriesTotalKosts.getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountKost));			
				}  
				else {
					kost = new Kost(currentAmount, currentCategory, SpendType.GAIN);
					listOfGains.add(kost);
					totalAmountGain+=currentAmount;

					//костыль
					@SuppressWarnings("deprecation")
					String currentMonth = monthNames.get(kost.getDate().getMonth());
					seriesTotalGains.getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountGain));
				}
				
				kost.setComment(textAreaComment.getText());
				kostTableData.add(kost);
				updateLabel();
				handleCancelButton();	
				
				@SuppressWarnings("deprecation")
				String currentMonth = monthNames.get(kost.getDate().getMonth());
				seriesTotalDifference.getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalAmountGain-totalAmountKost));
		}

		catch (NumberFormatException e1){
			Alert outputWindow = new Alert(AlertType.WARNING, "Sum must be a number! Please correct your data.");
			outputWindow.showAndWait();
				}	
		}
/*----------------------------------------Menu items-------------------------------------------------------------------------------------*/
	@FXML
	public void handleDeleteMenuItem(){		 
		Kost selectedItem = tableOfKosts.getSelectionModel().getSelectedItem();		
		tableOfKosts.getItems().remove(selectedItem);
		evaluateTotalAmount(selectedItem);
		updateLabel();		
		updateBarChartData(selectedItem);
		
		//String updateName = addEuroSign(selectedItem.getAmount(), selectedItem.getPurpose()); //selectedItem.getCategory() -> currentName if want to use addEuroSign
		for (Data d : pieChartData)
		{
			if (d.getName().equals(selectedItem.getCategory())) { //category -> updateName if want to use addEuroSign
				d.setPieValue(d.getPieValue()-selectedItem.getAmount());
				pieChartData.removeIf(x -> x.getPieValue()==0);
				return;
			}
		}
	}

	@FXML
	public void handleDeleteAllMenuItem(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Look, a Confirmation Dialog");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			pieChartData.clear();
			
			seriesTotalKosts.getData().clear();
			seriesTotalGains.getData().clear();
			seriesTotalDifference.getData().clear();
			//barChart.setLayoutY(10);
			
			tableOfKosts.getItems().clear();
			totalAmountGain = 0;
			totalAmountKost = 0;
			updateLabel();
		} 
		else alert.close();			
	}
	
	@FXML
	public void handleAboutItem(){
		Alert infoWindow = new Alert(AlertType.INFORMATION, "Version 1.0.0, Authors Don Okunitto & Don Voronitto");
		infoWindow.showAndWait();
	}

	@FXML
	public void handleExitItem(){
		main.primaryStage.close();
	}
/*-----------------------------Charts updaters----------------------------------------------------------------------------------------*/	
	public void updatePieChartData(String category, double sum){
		//String updateName = addEuroSign(sum, purpose); ;
		for (Data d : pieChartData)
		{
			if (d.getName().equals(category)) { //category -> updateName if want to use addEuroSign
				d.setPieValue(d.getPieValue()+sum);
				return;
			}
		}
		pieChartData.add(new PieChart.Data(category, sum));
	}

	public void updateBarChartData(Kost kost){
		@SuppressWarnings("deprecation")
		String currentMonth = monthNames.get(kost.getDate().getMonth());
		seriesTotalKosts.getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountKost));
		seriesTotalGains.getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountGain));
		seriesTotalDifference.getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalAmountGain-totalAmountKost));
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

		//currently unused
		public String addEuroSign(double amount, String name){
		return name + " " + amount + " И";
	}
/*----------------------------Buttons events------------------------------------------------------------------------------------*/
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
	
	@FXML
	public void handleCancelButton(){
		textFieldCategory.clear();
		textFieldSum.clear();
		textAreaComment.clear();
		radioKost.setSelected(true);
	}
}
