package com.mother.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.JDBC;
import com.main.Main;
import com.main.Messages;
@WebServlet("/viewmidwifemsgcount")
public class ViewMidwifeMsgCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		Messages msgs = new Messages();
		JDBC jdbc = new JDBC();;
		int midwifeMsgCount = 0;
		String midwifeID=null;
		try{
			String q = "SELECT midwifeID FROM area WHERE areaCode = (SELECT guardianAreaCode FROM guardian WHERE guardianID = '"+mid+"') ;";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				midwifeID = rs.getString("midwifeID");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				jdbc.conn.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		Main m = new Main();
		midwifeMsgCount += msgs.getUnreadMessageCount(midwifeID, mid);
		String messageCount = Integer.toString(midwifeMsgCount);
		out.print(messageCount);
	}
}
