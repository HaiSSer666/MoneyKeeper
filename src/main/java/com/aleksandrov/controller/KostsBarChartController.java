package com.aleksandrov.controller;

import java.time.Month;
import java.util.Arrays;
import com.aleksandrov.dao.KostDao;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class KostsBarChartController {
	//links to dao and main controller
	public MainController guiController;
	KostDao kostDao = new KostDao();

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
		restoreBarChart();
		seriesTotalKosts.setName("Total kosts");
		seriesTotalGains.setName("Total gains");
		seriesTotalDifference.setName("Difference");
		barChart.setTitle("Chart of total balance");
		barChart.getData().addAll(seriesTotalKosts, seriesTotalGains, seriesTotalDifference);
		monthNames.addAll(Arrays.asList(monthsOfYear));
		xAxis.setCategories(monthNames); 
		yAxis.setAutoRanging(true);
	}

	public void updateBarChartData(Kost kost){
		//double totalAmountKost = kostDao.getTotalAmount(SpendType.KOST);
		//double totalAmountGain = kostDao.getTotalAmount(SpendType.GAIN);
		//String currentMonth = String.valueOf(guiController.datePicker.getValue().getMonth());
		
		Month month = kost.getDateOfPurchaseOrIncome().getMonth();
		String currentMonth = String.valueOf(kost.getDateOfPurchaseOrIncome().getMonth());
		double totalMonthlyKost = kostDao.getTotalAmount(SpendType.KOST, month);
		double totalMonthlyGain = kostDao.getTotalAmount(SpendType.GAIN, month);
				
		seriesTotalKosts.getData().add(new XYChart.Data<String, Double>(currentMonth, totalMonthlyKost));
		seriesTotalGains.getData().add(new XYChart.Data<String, Double>(currentMonth, totalMonthlyGain));
		seriesTotalDifference.getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalMonthlyGain-totalMonthlyKost));
	}

	//test
	public void restoreBarChart() {
		kostDao.getAllKosts().forEach(kost -> {
			Month month = kost.getDateOfPurchaseOrIncome().getMonth();
			String currentMonth = String.valueOf(kost.getDateOfPurchaseOrIncome().getMonth());
			double totalMonthlyKost = kostDao.getTotalAmount(SpendType.KOST, month);
			double totalMonthlyGain = kostDao.getTotalAmount(SpendType.GAIN, month);
			if(kost.getSpendType()==SpendType.KOST){
				seriesTotalKosts.getData().add(new XYChart.Data<String, Double>(currentMonth, totalMonthlyKost));
			}
			else{
				seriesTotalGains.getData().add(new XYChart.Data<String, Double>(currentMonth, totalMonthlyGain));	
			}
			seriesTotalDifference.getData().add(new javafx.scene.chart.XYChart.Data<String, Double>(currentMonth, totalMonthlyGain-totalMonthlyKost));
		});
	}
}
