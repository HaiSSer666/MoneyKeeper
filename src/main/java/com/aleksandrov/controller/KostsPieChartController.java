package com.aleksandrov.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class KostsPieChartController {
	public MainController guiController;
	
	@FXML
	private PieChart pieChart;
	
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
		pieChart.setData(pieChartData);
		pieChart.setLegendVisible(false);
		pieChart.setTitle("Distribution of kosts");		
		pieChart.setLegendSide(Side.BOTTOM);
	}
	
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
}
