package com.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;

import com.mysql.jdbc.EscapeTokenizer;
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String uname = request.getParameter("username");
			String pword = request.getParameter("password");
			Main m = new Main();
			String id = null;
			String namepic[] = null;
			String user = null;
			String password = null;
			JDBC jdbc = new JDBC();
			HttpSession session = null;
			try{
				String q = "SELECT * FROM users WHERE userName = '"+uname+"'";
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
				while(rs.next()){
					password = (String) rs.getString("password");
					id = (String) rs.getString("userID");
				}
				if(id != null){
					String table = m.getPerson(id);
					if(table.equals("midwife")){
						namepic = getName(table, "name", "midwifePicture", "midwifeID", id);
						user = "Midwife";
					}
					else if(table.equals("mother")){
						namepic = getName("guardian", "guardianName", "guardianPicture", "guardianID", id);
						if(m.isHave("mother", "guardianID", id)){
							user = "Mother";
						}
						else{
							user = "Guardian";
						}
					}
					else if(table.equals("supervisor")){
						namepic = getName(table, "name", "supervisorPicture", "supervisorID", id);
						user = "Supervisor";
					}
					else if(table.equals("admin")){
						namepic = getName(table, "name", "adminPicture", "adminID", id);
						user = "Admin";
					}
				}
				else{
					request.setAttribute("warning", "<script>showalert(\"The Username you've entered is not registered\")</script>");
					getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
					return;
					//response.sendRedirect("index.jsp");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(pword.equals(password)){
				session = request.getSession();
				java.util.Date date = new java.util.Date();
				DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
				session.setAttribute("date", frmt.format(date));
				//session.setAttribute("user", namepic[0]);
				session.setAttribute("mid", id);
				//session.setAttribute("pic", namepic[1]);
				session.setAttribute("usertype", user);
				session.setMaxInactiveInterval(30*60);
				Cookie userName = new Cookie("user", namepic[0]);
				userName.setMaxAge(30*60);
				response.addCookie(userName);
				//getServletContext().getRequestDispatcher("/frontpage.jsp").forward(request, response);
				if(user.equals("Midwife")){
					response.sendRedirect("midwife/midwifefrontpage.jsp");
				}
				else if(user.equals("Mother")||user.equals("Guardian")){
					response.sendRedirect("mother/motherfrontpage.jsp");
				}
				else if(user.equals("Supervisor")){
					response.sendRedirect("supervisor/supervisorfrontpage.jsp");
				}
				else if(user.equals("Admin")){
					response.sendRedirect("admin/adminfrontpage.jsp");
				}
			}
			else{
				request.setAttribute("warning", "<script>showalert(\"The Password you've entered is incorrect\")</script>");
				getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
				//response.sendRedirect("index.jsp");
			}
		}catch (Exception e) {
			response.sendRedirect("/EMidwify");
		}
	}
	String[] getName(String table, String column1, String column2,String idColumn, String id){
		JDBC jdbc = new JDBC();;
		String name = null;
		String picture = null;
		try{
			String q = "SELECT "+column1+","+column2+" FROM "+table+" WHERE "+idColumn+" = '"+id+"';";
			jdbc.st.execute(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				name = (String) rs.getString(column1);
				picture = (String) rs.getString(column2);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String s[] = {name,picture}; 
		return s;
	}
}
