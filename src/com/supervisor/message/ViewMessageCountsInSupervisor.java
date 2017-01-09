package com.supervisor.message;

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
@WebServlet("/viewmessagecountsinsupervisor")
public class ViewMessageCountsInSupervisor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String sid = (String)session.getAttribute("mid");
		Messages msgs = new Messages();
		JDBC jdbc = new JDBC();;
		int midwifeMsgCount = 0;
		ArrayList<String> messagedMidwives = new ArrayList<String>();
		
		try{
			String q = "SELECT DISTINCT(senderID) FROM messages WHERE receiverID = '"+sid+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				messagedMidwives.add(rs.getString("senderID"));
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
		for(int i=0;i<messagedMidwives.size();i++){
			Main m = new Main();
			if(m.isHave("midwife", "midwifeID", messagedMidwives.get(i))){
				midwifeMsgCount += msgs.getUnreadMessageCount(messagedMidwives.get(i), sid);
			}
		}
		String messageCount = Integer.toString(midwifeMsgCount);
		out.print(messageCount);
	}
}
