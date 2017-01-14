package com.main;

import java.io.BufferedReader;



import java.io.OutputStreamWriter;


import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.iap.Protocol;


import java.util.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.net.HttpURLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;



public class Main {
	public boolean isHave(String table ,String column ,String compare){
		boolean re = false;
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT * FROM "+table+" WHERE "+column+" = '"+compare+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			if(rs.next()){
				re = true;
			}
		}catch(Exception e){
			System.out.print(e);
		}finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return re;
	}
	public String generateID(String table, String prefix){
		String ID = null;
		JDBC jdbc = new JDBC();
		try{
			String q="SELECT "+table+"ID FROM "+table+" WHERE "+table+"ID NOT LIKE '%v' OR "+table+"ID NOT LIKE '%V'";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			int max = 0;
			while(rs.next()){
				String curstr = rs.getString(1);
				int cur = Integer.parseInt(curstr.substring(prefix.length())); 
				if(max<cur){
					max = cur;
				}
			}
			ID = prefix+Long.toString(max+1);
		}catch(Exception e){
			System.out.print(e);
		}finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ID;
	}
	public String generateCode(String table, String prefix){
		String ID = null;
		JDBC jdbc = new JDBC();
		try{
			String q="SELECT * FROM "+table+";";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			int max = 0;
			while(rs.next()){
				String curstr = rs.getString(1);
				int cur = Integer.parseInt(curstr.substring(prefix.length())); 
				if(max<cur){
					max = cur;
				}
			}
			ID = prefix+Long.toString(max+1);
		}catch(Exception e){
			System.out.print(e);
		}finally{
			try {
				jdbc.conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ID;
	}
	public String convertID(String id){
		String prefix;
		String sufix;
		String finalid;
		int l = id.length();
		if((id.charAt(l-1)=='v')||(id.charAt(l-1)=='V')){
			prefix = id.substring(0,l-1);
			sufix = id.substring(l-1,l);
			finalid = prefix+sufix.toLowerCase();
		}
		else{
			prefix = id.substring(0, 1);
			sufix = id.substring(1);
			finalid = prefix.toUpperCase()+sufix;
		}
		return finalid;
	}
	public String getPerson(String id){
		String person = null;
		if(isHave("guardian", "guardianID", id)){
			person = "mother";
		}
		else if(isHave("midwife", "midwifeID", id)){
			person = "midwife";
		}
		else if(isHave("supervisor", "supervisorID", id)){
			person = "supervisor";
		}
		else if(isHave("admin", "adminID", id)){
			person = "admin";
		}
		return person;
	}
	public String userRegister(String id,String username,String password){
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT * FROM users WHERE userName = '"+username+"'";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				return "already registered";
			}
			q = "INSERT INTO users (userID,userName,password) VALUES (?,?,?);";
			java.sql.PreparedStatement pst=jdbc.conn.prepareStatement(q);
			pst.setString(1, id);
			pst.setString(2, username);
			pst.setString(3, encript(password));
			pst.executeUpdate();
			return "User registered successfully";            
        }catch(Exception e){
        	System.out.print(e);
        	return "Error occured";
        }
        finally{
	        try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	String encript(String in){
		Encryptor en;
		String out=null;
		try {
			en = new Encryptor();
			out=en.encrypt(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	String stripSlashes(String s){
		for(int i=0;i<s.length()-1;i++){
			if((s.charAt(i)=='\\')&&s.charAt(i+1)=='\\'){
				s = s.substring(0,i)+s.substring(i+1);
				i++;
			}else if(s.charAt(i)=='\\'){
				s = s.substring(0,i)+s.substring(i+1);
			}
		}
		return s;
	}
	public void sendSms(String number,String message) {
		JDBC jdbc = new JDBC();
		try {
			String q = "SELECT * FROM smsalerts WHERE number = ? && message = ?;";
			java.sql.PreparedStatement pst=jdbc.conn.prepareStatement(q);
			pst.setString(1, number);
			pst.setString(2, message);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			if(!rs.next()){
				q = "INSERT INTO smsalerts (number,message) VALUES(?,?);";
				pst=jdbc.conn.prepareStatement(q);
				pst.setString(1, number);
				pst.setString(2, message);
				pst.executeUpdate();
				
				send(number, message);
			}
			
			
			
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
		}finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void sendEmail(String toAddress,String message) throws AddressException,MessagingException {
		String host = "smtp.gmail.com";
		String port = "587";
		final String userName = "emidwify@gmail.com";
		final String password = "UCsc@123";
		String subject = "reset password";
		
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new java.util.Date());
        msg.setText(message);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
	
	public void send(String number, String message){
		
		try{
			// Construct data
			String user = "username=" + "dhanushkasampath.mtr@gmail.com";
			String hash = "&hash=" + "15de74a05f99388540e057b1c3f514eaf749f64c";
			message = "&message=" + message;
			String sender = "&sender=" + "Emidwify";
			String numbers = "&numbers=" + "94"+number.substring(1);
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("http://api.txtlocal.com/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String sendCode(String username){
		String code = UUID.randomUUID().toString();
		JDBC jdbc = new JDBC();
		String mobilenumber = null;
		String email = null;
		String person = null;
		person = getPerson(username);
		if(person == null){
			return "Username not registered in the system";
		}
		try {
			String q=null;
			String q2=null;
			if(person.equals("midwife")){
				q = "SELECT mobileNumber FROM midwifemobilenumber WHERE midwifeID = '"+username+"';";
				q2 = "SELECT email FROM midwife WHERE midwifeID = '"+username+"';";
			}
			else if(person.equals("mother")||person.equals("guardian")){
				q = "SELECT guardianMobileNumber FROM guardianmobilenumber WHERE guardianID = '"+username+"';";
				q2 = "SELECT guardianEmail FROM guardian WHERE guardianID = '"+username+"';";
			}
			else if(person.equals("supervisor")){
				q = "SELECT mobileNumber FROM supervisormobilenumber WHERE supervisorID = '"+username+"';";
				q2 = "SELECT email FROM supervisor WHERE supervisorID = '"+username+"';";
			}
			else if(person.equals("admin")){
				q = "SELECT mobileNumber FROM adminmobilenumber WHERE adminID = '"+username+"';";
				q2 = "SELECT email FROM admin WHERE adminID = '"+username+"';";
			}
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				mobilenumber = rs.getString(1);
			}
			st.executeQuery(q2);
			rs = st.getResultSet();
			while(rs.next()){
				email = rs.getString(1);
			}
			if((mobilenumber==null)&&(email==null)){
				return "You don't have mobile number send a request to MOH office change the password";
			}
			if((mobilenumber.equals(""))&&(email.equals(""))){
				return "You don't have mobile number send a request to MOH office change the password";
			}
			if(email!=null){
				q = "DELETE FROM resetpassword WHERE username = ?;";
				java.sql.PreparedStatement pst=jdbc.conn.prepareStatement(q);
				pst.setString(1, username);
				pst.executeUpdate();
				q = "INSERT INTO resetpassword (username,code) VALUES(?,?);";
				pst=jdbc.conn.prepareStatement(q);
				pst.setString(1, username);
				pst.setString(2, code);
				pst.executeUpdate();
				
				sendEmail(email, code);
				return "code have been sent you by a email";
			}
			if(mobilenumber!=null){
				q = "DELETE FROM resetpassword WHERE username = ?;";
				java.sql.PreparedStatement pst=jdbc.conn.prepareStatement(q);
				pst.setString(1, username);
				pst.executeUpdate();
				q = "INSERT INTO resetpassword (username,code) VALUES(?,?);";
				pst=jdbc.conn.prepareStatement(q);
				pst.setString(1, username);
				pst.setString(2, code);
				pst.executeUpdate();
				
				
				send(mobilenumber, code);
				return "code have been sent you by a SMS";
			}
		}catch (Exception e) {
			System.out.println(e);
		}finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "error";
	}
	public boolean checkCode(String username,String code){
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT code FROM resetpassword WHERE username = ?;";
			java.sql.PreparedStatement pst=jdbc.conn.prepareStatement(q);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			String dbcode = null;
			while(rs.next()){
				dbcode = stripSlashes(rs.getString("code"));
			}
			if(dbcode==null){
				return false;
			}
			if(code.equals(dbcode)){
				q = "DELETE FROM resetpassword WHERE username = ?;";
				pst=jdbc.conn.prepareStatement(q);
				pst.setString(1, username);
				pst.executeUpdate();
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean resetPassword(String username, String password){
		JDBC jdbc = new JDBC();
		try{
			String q = "UPDATE users SET password = ? WHERE userName = ?;";
			java.sql.PreparedStatement pst=jdbc.conn.prepareStatement(q);
			pst.setString(1, encript(password));
			pst.setString(2, username);
			pst.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
