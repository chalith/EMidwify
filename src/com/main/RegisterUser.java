package com.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.JDBC;
import com.main.Main;
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Main m = new Main();
		String username = request.getParameter("txtusername");
		String password = request.getParameter("txtpassword");
		String alert=m.userRegister(username, username, password);
		String finalAlert="";
		if(alert.equals("already registered")){
			finalAlert="<script>showalert(\"User is already registered in the system\")</script>";
		}else{
			finalAlert="<script>showsuccessmessage(\""+alert+"\")</script>";
		}
		request.setAttribute("finalAlert",finalAlert);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
	}
}
