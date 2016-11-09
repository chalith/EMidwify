package com.main;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Messages {
	JDBC jdbc = null;
	public ArrayList<String[]> ViewMessages(String sender, String receiver){
		ArrayList<String[]> messages = null;
		try{
			jdbc = new JDBC();
			String q = "SELECT senderID,message,sendTime FROM messages WHERE ((senderID = '"+sender+"') && (receiverID = '"+receiver+"')) || (senderID = '"+receiver+"') && (receiverID = '"+sender+"') ORDER BY sendTime;";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			messages = new ArrayList<String[]>();
			while(rs.next()){
				String messagesender = (String) rs.getString("senderID");
				String message = (String) rs.getString("message");
				String sendTime = (String) rs.getString("sendTime");
				String s[] = {messagesender,message,sendTime};
				messages.add(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				jdbc.conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return messages;
	}
	public void sendMessages(String sender, String message, String receiver){
		jdbc = new JDBC();
		try{
			java.util.Date cDate = new java.util.Date();
			DateFormat frmt = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
			String currentDate = frmt.format(cDate);
			String q = "INSERT INTO messages (senderID,receiverID,message,sendTime) VALUES('"+sender+"','"+receiver+"','"+message+"','"+currentDate+"');";
			jdbc.st.executeUpdate(q);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public int getUnreadMessageCount(String sender, String receiver){
		jdbc = new JDBC();
		int messageCount = 0;
		try{
			String q = "SELECT COUNT(senderID) FROM messages WHERE ( senderID = '"+sender+"' ) && ( receiverID = '"+receiver+"' ) && (receiveTime IS NULL);";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				messageCount = Integer.parseInt((String) rs.getString(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return messageCount;
	}
	public void setMessageRead(String sender, String receiver){
		jdbc = new JDBC();
		try{
			java.util.Date cDate = new java.util.Date();
			DateFormat frmt = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
			String currentDate = frmt.format(cDate);
			String q = "UPDATE messages SET receiveTime = '"+currentDate+"' WHERE ( senderID = '"+sender+"' ) && ( receiverID = '"+receiver+"' ) && (receiveTime IS NULL);";
			jdbc.st.executeUpdate(q);
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
