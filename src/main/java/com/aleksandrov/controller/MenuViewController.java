package com.aleksandrov.controller;

import java.sql.SQLException;
import java.util.Optional;

import com.aleksandrov.dao.KostDao;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

/**
 * separate controller for menu bar
 * @author Oleksii A.
 */

public class MenuViewController {
	public MainController guiController;
	KostDao kostDao = new KostDao();

	//menu items
	@FXML MenuItem aboutItem;
	@FXML MenuItem exitItem;
	@FXML MenuItem deleteItem;
	@FXML MenuItem deleteAllItem;

	//link to main controller
	public void setGuiController(MainController guiController) {
		this.guiController = guiController;
	}

	@FXML
	public void handleDeleteMenuItem(){		 
		ObservableList<PieChart.Data> pieChartData = guiController.kostsPieChartController.getPieChartData();	
		TableView<Kost> tableOfKosts = guiController.kostsTableViewController.tableOfKosts;
		Kost selectedItem = tableOfKosts.getSelectionModel().getSelectedItem();		

		if(selectedItem==null){
			Alert infoWindow = new Alert(AlertType.INFORMATION, "Please choose item at first!");
			infoWindow.showAndWait();
		}
		else{
			try {
				kostDao.deleteKost(selectedItem.getId());			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tableOfKosts.getItems().remove(selectedItem);	
			guiController.kostsBarChartController.updateBarChartData(selectedItem);
			double totalAmountKost = kostDao.getTotalAmount(SpendType.KOST);
			double totalAmountGain = kostDao.getTotalAmount(SpendType.GAIN);
			guiController.statusBarController.updateLabel(totalAmountKost, totalAmountGain);
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
	}

	@FXML
	public void handleDeleteAllMenuItems(){
		TableView<Kost> tableOfKosts = guiController.kostsTableViewController.tableOfKosts;
		XYChart.Series<String, Double> seriesTotalKosts = guiController.kostsBarChartController.getSeriesTotalKosts();
		XYChart.Series<String, Double> seriesTotalGains = guiController.kostsBarChartController.getSeriesTotalGains();
		XYChart.Series<String, Double> seriesTotalDifference = guiController.kostsBarChartController.getSeriesTotalDifference();
		ObservableList<PieChart.Data> pieChartData = guiController.kostsPieChartController.getPieChartData();

		if(tableOfKosts.getItems().size()>=1){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Look, a Confirmation Dialog");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				try {
					kostDao.deleteDbKost();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				double totalAmountKost = kostDao.getTotalAmount(SpendType.KOST);
				double totalAmountGain = kostDao.getTotalAmount(SpendType.GAIN);
				pieChartData.clear();
				seriesTotalKosts.getData().clear();
				seriesTotalGains.getData().clear();
				seriesTotalDifference.getData().clear();
				tableOfKosts.getItems().clear();
				guiController.statusBarController.updateLabel(totalAmountKost, totalAmountGain);
			} 
			else alert.close();	
		}
		else{
			showEmptyTableInfo();
		}
	}

	@FXML
	public void handleAboutItem(){
		Alert infoWindow = new Alert(AlertType.INFORMATION, "Version alpha 1.0.0, Author - Oleksii A.");
		infoWindow.showAndWait();
	}

	@FXML
	public void handleExitItem(){
		try {
			kostDao.CloseDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		guiController.main.primaryStage.close();	
	}

	public void showEmptyTableInfo(){
		Alert infoWindow = new Alert(AlertType.INFORMATION, "Table is empty!");
		infoWindow.showAndWait();
	}
}
