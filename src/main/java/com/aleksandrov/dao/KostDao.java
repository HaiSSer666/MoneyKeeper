package com.aleksandrov.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import com.aleksandrov.utils.connection;

public class KostDao {  
	private static Statement statement = connection.createStatement();

	public void insertKost(Kost kost) throws SQLException{  
		String query="INSERT INTO 'Kost' ('ID', 'amount', 'category', 'spendType', 'dateOfPurchaseOrIncome', 'creationDate', 'comment') "
				+ "VALUES('"+kost.getId()+"','"+kost.getAmount()+"','"+kost.getCategory()+"','"+kost.getSpendType()+"','"+kost.getDateOfPurchaseOrIncome()+"','"+kost.getCreationDate()+"','"+kost.getComment()+"')"; 
		statement.execute(query);
	}  

	public List<Kost> getAllKosts(){
		List<Kost> kosts = new ArrayList<>();
		try {
			ResultSet resSet = statement.executeQuery("SELECT * FROM Kost");
			while(resSet.next())
			{
				String id = resSet.getString("ID");
				UUID uid = UUID.fromString(id);
				float amount = resSet.getFloat("amount");
				String  category = resSet.getString("category");
				String  dbSppendType = resSet.getString("spendType");
				String  dateStamp = resSet.getString("dateOfPurchaseOrIncome");//костыль?
				LocalDate dateOfPurchaseOrIncome = LocalDate.parse(dateStamp);
				String  creationDate = resSet.getString("creationDate");
				String  comment = resSet.getString("comment");
				SpendType spendType;
				if(dbSppendType.equals("KOST")){
					spendType=SpendType.KOST;
				}
				else{
					spendType=SpendType.GAIN;
				}
				Kost kost = new Kost(uid, amount, category, spendType, dateOfPurchaseOrIncome, creationDate, comment);
				kosts.add(kost); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return kosts;
	}

	
	public double getTotalAmount(SpendType spendType) {
		/*double amount = 0;
		try {
			String query = "SELECT SUM(amount) FROM 'Kost' WHERE spendType=\"KOST\"";
			ResultSet resSet = statement.executeQuery(query);
			System.out.println(resSet.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		//return amount;
		
		double totalAmount = 0;
		for(Kost kost : getAllKosts()){
			if(kost.getSpendType()==spendType){
				totalAmount+=kost.getAmount();
			}
		}
		return totalAmount;
	}

	public double getTotalAmount(SpendType spendType, Month month) {
		//TODO sql query instead of forEach
		/*
		 * SELECT * FROM 'Kost' WHERE spendType="KOST" AND ...
		 * 
		 */
		double totalAmount = 0;
		for(Kost kost : getAllKosts()){
			if(kost.getSpendType()==spendType && kost.getDateOfPurchaseOrIncome().getMonth()==month){
				totalAmount+=kost.getAmount();
			}
		}
		return totalAmount;
	}

	public HashSet<String> getListOfPieChartKosts() {
		HashSet<String> listOfKosts = new HashSet<String>();
		for(Kost kost : getAllKosts()){
				listOfKosts.add(kost.getCategory());
		}
		return listOfKosts;
	}

	public double getTotalCategoryAmount(String category, String month, int year) throws SQLException{  
		double searchTotalAmount = 0;
		for(Kost kost : getAllKosts()){
			String searchCategory = kost.getCategory();
			String searchMonth = String.valueOf(kost.getDateOfPurchaseOrIncome().getMonth());
			int searchYear = kost.getDateOfPurchaseOrIncome().getYear();
			if(category.equals(searchCategory)&&month.equals(searchMonth)&&year==searchYear){
				searchTotalAmount+=kost.getAmount();
			}
		}
		return searchTotalAmount;
	}
	
	public double getTotalCategoryAmount(String category) throws SQLException{  
		double searchTotalAmount = 0;
		for(Kost kost : getAllKosts()){
			String searchCategory = kost.getCategory();
			if(category.equals(searchCategory)){
				searchTotalAmount+=kost.getAmount();
			}
		}
		return searchTotalAmount;
	}
	
	public void deleteKost(UUID id) throws SQLException{  
		String query="DELETE FROM 'Kost' WHERE ID='"+id+"'"; 
		statement.execute(query);
	}

	public void deleteDbKost() throws SQLException{  
		String query="DELETE FROM 'Kost'";  
		statement.execute(query);
	}

	public void CloseDB() throws SQLException
	{
		statement.close();
		System.out.println("Connection closed");
	}	
}
