package com.aleksandrov.controller;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import com.aleksandrov.model.Kost;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class KostsBarChartController {
	public GUIController guiController;
	
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
	public void setGuiController(GUIController guiController) {
		this.guiController = guiController;
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {	
		barChart.setTitle("Chart of total balance");
		barChart.getData().addAll(seriesTotalKosts, seriesTotalGains, seriesTotalDifference);
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		monthNames.addAll(Arrays.asList(months));
		xAxis.setCategories(monthNames);     

		//отображает только тотал кост/гейн, а не по месяцам
		seriesTotalKosts.setName("Total kosts");
		seriesTotalGains.setName("Total gains");;
		seriesTotalDifference.setName("Difference");;
	}

	public void updateBarChartData(Kost kost){
		@SuppressWarnings("deprecation")
		String currentMonth = monthNames.get(kost.getDate().getMonth());
		seriesTotalKosts.getData().add(new XYChart.Data<String, Double>(currentMonth, guiController.totalAmountKost));
		seriesTotalGains.getData().add(new XYChart.Data<String, Double>(currentMonth, guiController.totalAmountGain));
		seriesTotalDifference.getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, guiController.totalAmountGain-guiController.totalAmountKost));
	}
}
