package com.midwife.clinic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.ClinicDates;
public class LoadClinicDates extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String areaCode = request.getParameter("area");
		ClinicDates clinicDates = new ClinicDates(areaCode);
		String dates = "<option selected disabled option>Clinic Dates</option>";
		ArrayList<String[]> cDates = clinicDates.getDates();
		for(int i=0;i<cDates.size();i++){
			dates = dates + "<option>"+cDates.get(i)[0]+"</option>";
		}
		out.print(dates);
	}
}
