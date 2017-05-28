package com.aleksandrov.controller;
/**
 * main GUI controller
 * Author Oleksii A.
 */
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
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
//import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GUIController {	
	//links to sub controllers
	@FXML
	public MenuViewController menuViewController;
	@FXML
	public KostsTableViewController kostsTableViewController;
	
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
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	
	/*--------------------------------------getters for sub controllers-----------------------------------------------------------------*/	
	public double getTotalAmountKost() {
		return totalAmountKost;
	}

	public double getTotalAmountGain() {
		return totalAmountGain;
	}

	public XYChart.Series<String, Double> getSeriesTotalKosts() {
		return seriesTotalKosts;
	}

	public XYChart.Series<String, Double> getSeriesTotalGains() {
		return seriesTotalGains;
	}

	public XYChart.Series<String, Double> getSeriesTotalDifference() {
		return seriesTotalDifference;
	}

	public ObservableList<PieChart.Data> getPieChartData() {
		return pieChartData;
	}
	
	/*--------------------------------------Start-up methods----------------------------------------------------------------------------*/		
	public GUIController() {
	}

	/*is used after fxml-file loading*/
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {	
		menuViewController.setGuiController(this);
		kostsTableViewController.setGuiController(this);

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
	}
	
	public void setMainApp(Main mainApp) {
		this.main = mainApp;
	}
	/*--------------------------------------------Buttons-----------------------------------------------------------------------------*/
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
					//listOfKosts.add(kost);
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
					//listOfGains.add(kost);
					totalAmountGain+=currentAmount;

					//костыль
					@SuppressWarnings("deprecation")
					String currentMonth = monthNames.get(kost.getDate().getMonth());
					seriesTotalGains.getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountGain));
				}
				
				kost.setComment(textAreaComment.getText());
				kostsTableViewController.kostTableData.add(kost);
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
	
	@FXML
	public void handleCancelButton(){
		textFieldCategory.clear();
		textFieldSum.clear();
		textAreaComment.clear();
		radioKost.setSelected(true);
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
