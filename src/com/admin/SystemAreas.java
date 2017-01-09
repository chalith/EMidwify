package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.JDBC;
@WebServlet("/systemareas")
public class SystemAreas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject data = new JSONObject();
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT * FROM area;";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			int i = 0;
			while(rs.next()){
				String areaCode = rs.getString("areaCode");
				String area = rs.getString("area");
				String mid = rs.getString("midwifeID");
				String midwifename = rs.getString("midwifeID");
				JDBC jdbc2 = new JDBC();
				String q2 = "SELECT * FROM midwife WHERE midwifeID = '"+mid+"';";
				jdbc2.st.executeQuery(q2);
				ResultSet rs2 = jdbc2.st.getResultSet();
				while(rs2.next()){
					midwifename = rs2.getString("name");
				}
				jdbc2.conn.close();
				JSONObject row = new JSONObject();
				row.put("areaCode", areaCode);
				row.put("area",area);
				row.put("midwifeID",mid);
				row.put("midwifeName",midwifename);
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
