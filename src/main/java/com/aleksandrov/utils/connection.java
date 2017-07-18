package com.aleksandrov.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connection {
	public static Connection dbConnection;
	public static Statement statement;

	public static void connect() throws SQLException {
		dbConnection = DriverManager.getConnection("jdbc:sqlite:MoneyKeeper.s3db");
		System.out.println("Database is connected!");
	}

	public static Statement createStatement() {
		try {
			if(dbConnection==null){
				connect();
			}
			statement = dbConnection.createStatement();
		} catch (SQLException e) {
			System.out.println("Unable to create statement");
			e.printStackTrace();
		}
		return statement;
	}

	public static void createKostTable() {
		try {
			statement.execute("CREATE TABLE 'Kost' ("
					+ "ID TEXT PRIMARY KEY NOT NULL,"
					+ "'amount' FLOAT NOT NULL,"
					+ "'category'	TEXT NOT NULL,"
					+ "'spendType'	TEXT NOT NULL,"
					+ "'dateOfPurchaseOrIncome'	DATE NOT NULL DEFAULT CURRENT_DATE,"
					+ "'creationDate'	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ "'comment' TEXT NOT NULL);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
