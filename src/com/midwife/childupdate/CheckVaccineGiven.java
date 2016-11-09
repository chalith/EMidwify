package com.midwife.childupdate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.VaccineAmounts;
public class CheckVaccineGiven extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
    	String cid = request.getParameter("id");
    	String vCode = request.getParameter("vcode");
    	String date = request.getParameter("date");
    	VaccineAmounts vamounts = new VaccineAmounts(cid, date, 0);
    	out.print(vamounts.checkVaccineGiven(vCode));
	}
}
