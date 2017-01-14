package com.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/checkresetcode")
public class CheckResetCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Main m = new Main();
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String code = request.getParameter("code");
		boolean x = m.checkCode(username, code);
		if(x){
			out.print("code is correct");
		}else{
			out.print("code is incorrect");
		}
	}

}
