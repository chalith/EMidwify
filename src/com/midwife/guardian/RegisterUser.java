package com.midwife.guardian;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.JDBC;
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("txtusername");
		String password = request.getParameter("txtpassword");
		JDBC jdbc = new JDBC();
		try{
			String q = "INSERT INTO users (userID,userName,password) VALUES ('"+username+"','"+username+"','"+password+"');";
			jdbc.st.executeUpdate(q);
			request.setAttribute("finalAlert","<script>showsuccessmessage(\"User registered successfully\")</script>");
            out.println("successfully added");
        }catch(Exception e){
        	System.out.print(e);
        	request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
        }
        finally{
	        try {
				jdbc.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        getServletContext().getRequestDispatcher("/midwife/motherregistration.jsp").forward(request,response);
	}
}
