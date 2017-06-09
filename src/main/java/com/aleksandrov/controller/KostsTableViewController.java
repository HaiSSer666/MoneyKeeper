package com.aleksandrov.controller;

import java.time.LocalDate;
import com.aleksandrov.dao.KostDao;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KostsTableViewController {
	public MainController guiController;
	KostDao kostDao = new KostDao();
	
	//lists
	@FXML
	public ObservableList<Kost> kostTableData = FXCollections.observableArrayList();
	
	//table view and columns
	@FXML TableView<Kost> tableOfKosts;
	@FXML TableColumn<Kost, Double> columnAmount;
	@FXML TableColumn<Kost, String> columnCategory;
	@FXML TableColumn<Kost, SpendType> columnType;
	@FXML TableColumn<Kost, LocalDate> columnDateOfPurchaseOrIncome;
	@FXML TableColumn<Kost, String> columnDate;
	@FXML TableColumn<Kost, String> columnComment;
	
	//link to main controller
	public void setGuiController(MainController guiController) {
		this.guiController = guiController;
	}
	
	@FXML
	private void initialize() {		
		kostTableData.addAll(kostDao.getAllKosts());
		columnAmount.setCellValueFactory(new PropertyValueFactory<Kost, Double>("amount"));	
		columnCategory.setCellValueFactory(new PropertyValueFactory<Kost, String>("category"));		
		columnType.setCellValueFactory(new PropertyValueFactory<Kost, SpendType>("spendType"));	
		columnDateOfPurchaseOrIncome.setCellValueFactory(new PropertyValueFactory<Kost, LocalDate>("dateOfPurchaseOrIncome"));
		columnDate.setCellValueFactory(new PropertyValueFactory<Kost, String>("creationDate"));	
		columnComment.setCellValueFactory(new PropertyValueFactory<Kost, String>("comment"));
		tableOfKosts.setItems(kostTableData);
	}
}