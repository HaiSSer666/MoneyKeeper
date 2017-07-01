package com.aleksandrov.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
		double totalAmount = 0;
		for(Kost kost : getAllKosts()){
			if(kost.getSpendType()==spendType){
				totalAmount+=kost.getAmount();
			}
		}
		return totalAmount;
	}
	
	public ArrayList<String> getListOfPieChartKosts() {
		ArrayList<String> listOfKosts = new ArrayList<String>();
		for(Kost kost : getAllKosts()){
			if(kost.getSpendType()==SpendType.KOST){
				String category = kost.getCategory();
				listOfKosts.add(category);
			}
		}
		return listOfKosts;
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
		System.out.println("Соединения закрыты");
	   }
}
