package com.aleksandrov.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connection {
	public static Connection dbConnection;
	public static Statement statement;
	
	public static void connect() throws ClassNotFoundException, SQLException {
		dbConnection = null;
		Class.forName("org.sqlite.JDBC");
		dbConnection = DriverManager.getConnection("jdbc:sqlite:MoneyKeeper.s3db");
		System.out.println("Database is connected!");
		statement = dbConnection.createStatement();
	}
}
