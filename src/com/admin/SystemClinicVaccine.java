package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.JDBC;
@WebServlet("/systemclinicvaccine")
public class SystemClinicVaccine extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
		JSONObject data = new JSONObject();
		JDBC jdbc = new JDBC();
		String table = request.getParameter("table");
		try{
			String q = "SELECT v.vaccineName as name,c.vaccineCode as code,c.amount as amount,c.age as age FROM vaccine v JOIN "+table+" c ON v.vaccineCode = c.vaccineCode;";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			int i = 0;
			while(rs.next()){
				String code = rs.getString("code");
				String name = rs.getString("name");
				String age = rs.getString("age");
				String amount = rs.getString("amount");
				JSONObject row = new JSONObject();
				row.put("code", code);
				row.put("name",name);
				row.put("age",age);
				row.put("amount",amount);
				data.put(Integer.toString(i), row);
				i++;
			}
			out.print(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				jdbc.conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
    }

}
