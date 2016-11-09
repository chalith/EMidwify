package com.midwife.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.json.JSONObject;

import com.main.JDBC;
import com.main.Main;
import com.main.Messages;
@WebServlet("/viewcounts")
public class ViewCounts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		Messages msgs = new Messages();
		JDBC jdbc = new JDBC();;
		int motherMsgCount = 0;
		int supervisorMsgCount = 0;
		try{
			String q = "SELECT DISTINCT(senderID) FROM messages WHERE receiverID = '"+mid+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				String s = rs.getString("senderID");
				Main m = new Main();
				if(m.isHave("guardian", "guardianID", s)){
					motherMsgCount += msgs.getUnreadMessageCount(s, mid);
				}
			}
			q = "SELECT DISTINCT(supervisorID) FROM midwife WHERE midwifeID = '"+mid+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs2 = jdbc.st.getResultSet();
			while(rs2.next()){
				String s = rs2.getString("supervisorID");
				supervisorMsgCount += msgs.getUnreadMessageCount(s, mid);
			}
			JSONObject json = new JSONObject();
			json.put("messagecount", supervisorMsgCount+motherMsgCount);
			json.put("supervisormessagecount", supervisorMsgCount);
			json.put("mothermessagecount", motherMsgCount);
			out.print(json);
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
	}

}
