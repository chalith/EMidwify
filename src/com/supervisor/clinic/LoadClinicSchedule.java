package com.supervisor.clinic;

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
@WebServlet("/loadclinicschedule")
public class LoadClinicSchedule extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String year = request.getParameter("year");
		String area = request.getParameter("area");
		JSONObject data = new JSONObject();
		JDBC jdbc = new JDBC();
		try{
			String q = "SELECT * FROM clinics WHERE areaCode = '"+area+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			int i = 0;
			while(rs.next()){
				String cdate = rs.getString("clinicDate");
				String cvenue = rs.getString("venue");
				String ctime = rs.getString("time");
				if(cdate.startsWith(year)){
					JSONObject row = new JSONObject();
					row.put("date", cdate);
					row.put("venue",cvenue);
					row.put("time",ctime);
					data.put(Integer.toString(i), row);
					i++;
				}
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
