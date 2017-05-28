package com.aleksandrov.controller;

import java.util.Date;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KostsTableViewController {
	public GUIController guiController;
	
	@FXML ObservableList<Kost> kostTableData = FXCollections.observableArrayList();
	
	@FXML TableView<Kost> tableOfKosts;
	
	@FXML
	TableColumn<Kost, Double> columnAmount;
	@FXML
	TableColumn<Kost, String> columnCategory;
	@FXML
	TableColumn<Kost, SpendType> columnType;
	@FXML
	TableColumn<Kost, Date> columnDate;
	@FXML
	TableColumn<Kost, String> columnComment;
	
	//link to main controller
	public void setGuiController(GUIController guiController) {
		this.guiController = guiController;
	}
	
	//2
	public TableView<Kost> getTableOfKosts() {
		return tableOfKosts;
	}
	
	@FXML
	private void initialize() {		
		columnAmount.setCellValueFactory(new PropertyValueFactory<Kost, Double>("amount"));	
		columnCategory.setCellValueFactory(new PropertyValueFactory<Kost, String>("category"));		
		columnType.setCellValueFactory(new PropertyValueFactory<Kost, SpendType>("spendType"));	
		columnDate.setCellValueFactory(new PropertyValueFactory<Kost, Date>("date"));	//LocalDate -> Date, dateOfPurchaseIncome -> date
		columnComment.setCellValueFactory(new PropertyValueFactory<Kost, String>("comment"));
		tableOfKosts.setItems(kostTableData);	
	}
}
