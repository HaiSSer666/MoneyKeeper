package com.aleksandrov.dao;

import java.sql.SQLException;
import java.sql.Statement;
import com.aleksandrov.model.Kost;
import com.aleksandrov.utils.connection;  

public class KostDao {  
    private Statement statement;  
      
    public void saveKost(Kost kost) throws SQLException{  
        String query="INSERT INTO 'Kost' ('amount', 'category', 'spendType') VALUES('"+kost.getAmount()+"','"+kost.getCategory()+"','"+kost.getSpendType()+"')"; 
        statement = connection.dbConnection.createStatement();
        statement.execute(query);
    }  
   
    /*public void deleteKost(Kost kost) throws SQLException{  
        String query="DELETE kost where category='"+kost.getCategory()+"' ";  
        statement.execute(query);
    }  	*/
}
