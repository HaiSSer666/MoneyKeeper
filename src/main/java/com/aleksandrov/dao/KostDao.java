package com.aleksandrov.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;
import com.aleksandrov.utils.connection;  

public class KostDao {  
	private static Statement statement = connection.createStatement();

	public void insertKost(Kost kost) throws SQLException{  
		String query="INSERT INTO 'Kost' ('amount', 'category', 'spendType', 'dateOfPurchaseOrIncome', 'creationDate', 'comment') "
				+ "VALUES('"+kost.getAmount()+"','"+kost.getCategory()+"','"+kost.getSpendType()+"','"+kost.getDateOfPurchaseOrIncome()+"','"+kost.getCreationDate()+"','"+kost.getComment()+"')"; 
		statement.execute(query);
	}  

	
	
	public List<Kost> getAllKosts(){
		List<Kost> kosts = new ArrayList<>();
		try {
			ResultSet resSet = statement.executeQuery("SELECT * FROM Kost");
			while(resSet.next())
			{
				float amount = resSet.getFloat("amount");
				String  category = resSet.getString("category");
				String  spendType = resSet.getString("spendType");
				String  comment = resSet.getString("comment");
				String  dateStamp = resSet.getString("dateOfPurchaseOrIncome");//костыль?
			    LocalDate dateOfPurchaseOrIncome = LocalDate.parse(dateStamp);
				SpendType spendType2;
				if(spendType==SpendType.KOST.toString()){
					spendType2=SpendType.KOST;
				}
				else{
					spendType2=SpendType.GAIN;
				}
				kosts.add(new Kost(amount, category, spendType2, dateOfPurchaseOrIncome, comment)); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return kosts;
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
