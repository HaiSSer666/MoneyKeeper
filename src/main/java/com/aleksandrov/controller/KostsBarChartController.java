package com.aleksandrov.controller;

import java.util.Arrays;
import com.aleksandrov.model.Kost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class KostsBarChartController {
	public MainController guiController;

	//chart
	@FXML
	private BarChart<String, Double> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;

	//series
	XYChart.Series<String, Double> seriesTotalKosts = new XYChart.Series<>();
	XYChart.Series<String, Double> seriesTotalGains = new XYChart.Series<>();
	XYChart.Series<String, Double> seriesTotalDifference = new XYChart.Series<>();

	//lists
	private ObservableList<String> monthNames = FXCollections.observableArrayList();
	private String[] monthsOfYear = {"JANUARY",      
			"FEBRUARY",
			"MARCH",        
			"APRIL",        
			"MAY",          
			"JUNE",         
			"JULY",         
			"AUGUST",       
			"SEPTEMBER",    
			"OCTOBER",      
			"NOVEMBER",     
	"DECEMBER"};

	//getters
	public XYChart.Series<String, Double> getSeriesTotalKosts() {
		return seriesTotalKosts;
	}

	public XYChart.Series<String, Double> getSeriesTotalGains() {
		return seriesTotalGains;
	}

	public XYChart.Series<String, Double> getSeriesTotalDifference() {
		return seriesTotalDifference;
	}

	public ObservableList<String> getMonthNames() {
		return monthNames;
	}

	//link to main controller
	public void setGuiController(MainController guiController) {
		this.guiController = guiController;
	}

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {	
		//show total kosts/gains and not for every month separately - TODO
		seriesTotalKosts.setName("Total kosts");
		seriesTotalGains.setName("Total gains");
		seriesTotalDifference.setName("Difference");
		barChart.setTitle("Chart of total balance");
		barChart.getData().addAll(seriesTotalKosts, seriesTotalGains, seriesTotalDifference);
		monthNames.addAll(Arrays.asList(monthsOfYear));
		xAxis.setCategories(monthNames);     
	}

	public void updateBarChartData(Kost kost){
		double totalAmountKost = guiController.getTotalAmountKost();
		double totalAmountGain = guiController.getTotalAmountGain();
		String currentMonth = String.valueOf(guiController.datePicker.getValue().getMonth());
		seriesTotalKosts.getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountKost));
		seriesTotalGains.getData().add(new XYChart.Data<String, Double>(currentMonth, totalAmountGain));
		seriesTotalDifference.getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalAmountGain-totalAmountKost));
	}
}
