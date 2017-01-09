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
@WebServlet("/systemepidemics")
public class SystemEpidemics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject data = new JSONObject();
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT * FROM epidemic;";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			int i = 0;
			while(rs.next()){
				String epidemicCode = rs.getString("epidemicCode");
				String epidemicName = rs.getString("epidemicName");
				String description = rs.getString("description");
				JSONObject row = new JSONObject();
				row.put("epidemicCode", epidemicCode);
				row.put("epidemicName",epidemicName);
				row.put("description",description);
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
