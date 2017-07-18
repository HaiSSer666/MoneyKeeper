package com.aleksandrov.controller;

import java.sql.SQLException;

import com.aleksandrov.dao.KostDao;
import com.aleksandrov.model.SpendType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

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

	public void updatePieChartData(String newCategory, double newAmount)
	{
		//String newFormattedCategory = StringFormatUtil.addEuroSign(newCategory, newAmount); //TODO
		for(Data kost : pieChartData)
		{
			if(kost.getName().equals(newCategory)){
				double totalCategoryAmount = 0;
				try {
					totalCategoryAmount = kostDao.getTotalCategoryAmount(kost.getName());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				kost.setPieValue(totalCategoryAmount);
				return;
			}
		}
		pieChartData.add(new Data(newCategory, newAmount));
	}

	public void restorePieChart() {
		kostDao.getAllKosts().forEach(kost -> {
			if(kost.getSpendType()==SpendType.KOST){
				//String newFormattedCategory = StringFormatUtil.addEuroSign(kost.getCategory(), kost.getAmount()); //TODO
				updatePieChartData(kost.getCategory(), kost.getAmount());
			}
		});
	}
}

