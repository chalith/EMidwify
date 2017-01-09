package com.midwife.message;

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

import org.apache.catalina.Session;
import org.json.JSONException;
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
		String SupervisorID = null;
		ArrayList<String> messagedMothers = new ArrayList<String>();
		
		try{
			String q = "SELECT DISTINCT(senderID) FROM messages WHERE receiverID = '"+mid+"';";
			Statement st=jdbc.conn.createStatement();
			st.executeQuery(q);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				messagedMothers.add(rs.getString("senderID"));
			}
			q = "SELECT DISTINCT(supervisorID) FROM midwife WHERE midwifeID = '"+mid+"';";
			st.executeQuery(q);
			ResultSet rs2 = st.getResultSet();
			while(rs2.next()){
				SupervisorID=rs2.getString("supervisorID");
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
		supervisorMsgCount += msgs.getUnreadMessageCount(SupervisorID, mid);
		for(int i=0;i<messagedMothers.size();i++){
			Main m = new Main();
			if(m.isHave("guardian", "guardianID", messagedMothers.get(i))){
				motherMsgCount += msgs.getUnreadMessageCount(messagedMothers.get(i), mid);
			}
		}
		JSONObject json = new JSONObject();
		try {
			json.put("messagecount", supervisorMsgCount+motherMsgCount);
			json.put("supervisormessagecount", supervisorMsgCount);
			json.put("mothermessagecount", motherMsgCount);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(json);
	}

}
