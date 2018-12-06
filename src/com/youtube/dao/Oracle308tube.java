package com.youtube.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;

import javax.naming.*;
import javax.sql.*;

public class Oracle308tube {
	
	private static DataSource Oracle308tube = null; //hold the database object
	private static Context context = null; //used to lookup the database connection in weblogic
	
	public static DataSource Oracle308tubeConn() throws Exception {

		if (Oracle308tube != null) {
			return Oracle308tube;
		}

		try {

			if (context == null) {
				context = new InitialContext();
			}
			Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            Oracle308tube = (DataSource) envContext.lookup("jdbc/ecollect");
            //Oracle308tube = (DataSource) initContext.lookup("java:comp/env/jdbc/ecollectmysql");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return Oracle308tube;
	}
	
	public static Connection LocalConnection2(){
		Connection conn = null;
		String FILENAME = "/opt/sslcerts/passcode.txt";
		String username = null;
		String pass = null;
		String dbserver = null;
				
				try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
					String sCurrentLine;
					int i = 0;
					while ((sCurrentLine = br.readLine()) != null) {
						i++;
						
						if(i==1){
							username = sCurrentLine;
						}else if(i==2){
							pass = sCurrentLine;
						}else if(i==3){
							dbserver = sCurrentLine;
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	   try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = dbserver; 
			Locale.setDefault(Locale.US);
			conn = DriverManager.getConnection(url,username,pass);
			return conn;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection LocalConnection(){
		Connection conn = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//String url = "jdbc:oracle:thin:@129.100.9.202:1523:ecoltst";
			Locale.setDefault(Locale.US);
			//conn = DriverManager.getConnection(url,"ecol","ecol");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.78.149:1521:ecol", "ecol", "ecol");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/ORCLCDB.localdomain", "ecol", "ecol");
            
			return conn;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection mysqlConnection(){
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//String url = "jdbc:oracle:thin:@172.16.201.107:1521:COPECOLLECT"; 
			//conn = DriverManager.getConnection(url,"ecol","ecol");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ecollect?"
			              + "user=root&password=root");
			return conn;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return conn;
	}


}
