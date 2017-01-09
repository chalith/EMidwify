package com.midwife.viewchild;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.JDBC;
public class ViewChildVaccinations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();
		String id = request.getParameter("cid");
		String date = request.getParameter("clinicdate");
		JSONObject parent = new JSONObject();
		try{
			String q2="SELECT age,weight FROM childclinic WHERE ((clinicDate='"+date+"') && (childID = '"+id+"'))";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q2);
			ResultSet rs2 = st.getResultSet();
			String age = "";
			String weight = "";
			while(rs2.next()){
				age = (String)rs2.getString("age");
				weight = (String)rs2.getString("weight");
			}
			q2="SELECT amount FROM triposha WHERE ((date='"+date+"') && (id = '"+id+"'));";
			st.executeQuery(q2);
			ResultSet rs3 = st.getResultSet();
			
			String tamount = "0";
			while(rs3.next()){
				tamount = (String)rs3.getString("amount");
			}
			JSONObject ob = new JSONObject();
			ob.put("age",age);
			ob.put("weight", weight);
			ob.put("tamount", tamount);
			//System.out.print(age+" "+weight);
			parent.put("main", ob);
		}catch(Exception e){
			System.out.print(e);
		}
		try{

			String q3="SELECT vaccineCode,vaccineName,vaccineAmount FROM childgivenvaccines WHERE ((clinicDate='"+date+"') && (childID = '"+id+"'));";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q3);
			ResultSet rs3 = st.getResultSet();
			int i=0;
			while(rs3.next()){
				JSONObject ob1 = new JSONObject();
				ob1.put("code",(String)rs3.getString("vaccineCode"));
				ob1.put("name",(String)rs3.getString("vaccineName"));
				ob1.put("amount",(String)rs3.getString("vaccineAmount"));
				parent.put(Integer.toString(i), ob1);
				i++;
			}
		}
		catch(Exception e){
			System.out.print(e);
		}
    	try {
			jdbc.conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		out.print(parent);
	}
}
