package com.mother.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

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
		try{
			String q = "SELECT DISTINCT(senderID) FROM messages WHERE receiverID = '"+mid+"';";;
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				String s = rs.getString("senderID");
				Main m = new Main();
				if(m.isHave("midwife", "midwifeID", s)){
					midwifeMsgCount += msgs.getUnreadMessageCount(s, mid);
				}
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
		String messageCount = Integer.toString(midwifeMsgCount);
		out.print(messageCount);
	}
}
