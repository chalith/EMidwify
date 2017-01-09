package com.main;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
	public Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "emidwify";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "";
	public JDBC(){
		try{
			Class.forName(driver).newInstance();
	        conn = DriverManager.getConnection(url+dbName,userName,password);
		}catch(Exception e){
			System.out.print(e);
		}
	}
}
