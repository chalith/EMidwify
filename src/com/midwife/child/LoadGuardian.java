package com.midwife.child;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.main.JDBC;
import com.main.Main;

public class LoadGuardian extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String motherid = request.getParameter("txtmotherguardianid");
		HttpSession session = request.getSession(false);
		String mid = (String) session.getAttribute("mid");
		//System.out.println(motherid);
		String name = null;
		String area = null;
		String id = null;
		JDBC jdbc = new JDBC();;
		try{
			String q = "SELECT guardianID,guardianName,guardianAreaCode FROM guardian WHERE guardianID = '"+motherid+"' && guardianAreaCode IN (SELECT areaCode FROM area WHERE midwifeID = '"+mid+"');";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			JSONObject ob = new JSONObject();
			if(rs.next()){
				id = rs.getString("guardianID");
				name = rs.getString("guardianName");
				area = rs.getString("guardianAreaCode");
			}
			ob.put("name", name);
			Main m = new Main();
			if(m.isHave("mother", "guardianID", motherid)){
				ob.put("id", id);
				ob.put("area", area);
			}
			out.print(ob);
			//System.out.println(data);
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
