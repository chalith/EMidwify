package com.midwife.motherupdate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.Date;
import com.main.JDBC;
public class ViewMotherPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBC jdbc = new JDBC();
		PrintWriter out = response.getWriter();
		String gid = request.getParameter("gid");
		String name="";
		String picture="";
			
		try{
			String q = "SELECT guardianName,guardianPicture,guardianBDate FROM guardian WHERE guardianID = '"+gid+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				name = rs.getString("guardianName");
				picture = rs.getString("guardianPicture");
			}
			JSONObject ob = new JSONObject();
			ob.put("name",name);
			ob.put("picture", picture);
			out.print(ob);
		}catch(Exception e){
			System.out.println(e);
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
