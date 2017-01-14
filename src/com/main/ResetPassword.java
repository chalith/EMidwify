package com.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/resetpassword")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Main m = new Main();
		PrintWriter out = response.getWriter();
		String username = request.getParameter("resetusername");
		String password = request.getParameter("resetpassword");
		String finalAlert = "";
		if((username!=null)&&(password!=null)){
			if(m.resetPassword(username,password)){
				finalAlert="<script>showsuccessmessage(\"Password has been reset\")</script>";
			}else{
				finalAlert="<script>showalert(\"Please request for a code\")</script>";
				request.setAttribute("finalAlert",finalAlert);
			}
		}
		request.setAttribute("finalAlert",finalAlert);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
	}

}
