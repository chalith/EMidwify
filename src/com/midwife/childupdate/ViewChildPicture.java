package com.midwife.childupdate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.Date;
import com.main.JDBC;
public class ViewChildPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBC jdbc = new JDBC();
		PrintWriter out = response.getWriter();
		String cid = request.getParameter("cid");
		String name="";
		String picture="";
			
		try{
			String q = "SELECT childName,childPicture FROM child WHERE childID = '"+cid+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				name = rs.getString("childName");
				picture = rs.getString("childPicture");
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
