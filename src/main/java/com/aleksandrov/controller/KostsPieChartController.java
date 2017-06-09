package com.aleksandrov.controller;

import com.aleksandrov.dao.KostDao;
import com.aleksandrov.model.SpendType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

public class KostsPieChartController {
	public MainController guiController;
	KostDao kostDao = new KostDao();
	
	//pie chart
	@FXML
	private PieChart pieChart;
	
	//lists
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	
	//link to main controller
	public void setGuiController(MainController guiController) {
		this.guiController = guiController;
	}
	
	public ObservableList<PieChart.Data> getPieChartData() {
		return pieChartData;
	}
	
	@FXML
	private void initialize() {		
		restorePieChart();
		pieChart.setData(pieChartData);
		pieChart.setLegendVisible(false);
		pieChart.setTitle("Distribution of kosts");		
		pieChart.setLegendSide(Side.BOTTOM);
	}
	
	public void updatePieChartData(String category, double sum){
		//String updateName = addEuroSign(sum, purpose); TODO
		pieChartData.forEach(data -> {
			if (data.getName().equals(category)) { //category -> updateName if want to use addEuroSign
				data.setPieValue(data.getPieValue()+sum);
				return;
			}
		});
		pieChartData.add(new PieChart.Data(category, sum));
	}
	
	public void restorePieChart() {
		kostDao.getAllKosts().forEach(kost -> {
			if(kost.getSpendType()==SpendType.KOST){
				pieChartData.add(new PieChart.Data(kost.getCategory(), kost.getAmount()));
			}
		});
	}
}
