package com.midwife.clinic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.ChildInClinic;
import com.main.Clinic;
import com.main.ClinicDates;
public class LoadClinicChildren extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String area = request.getParameter("area");
		String date = request.getParameter("date");
		Clinic clinic = new Clinic(area, date);
		ArrayList<ChildInClinic> children = clinic.getChildren();
		ClinicDates cDates = new ClinicDates(area);
		String venuetime[] = cDates.getDeatils(date);
		JSONObject ob = new JSONObject();
		JSONObject ob1 = new JSONObject();
		JSONObject ob2 = new JSONObject();
		try{
			ob1.put("venue", venuetime[0]);
			ob1.put("time", venuetime[1]);
			for(int i=0;i<children.size();i++){
				JSONObject ob4 = new JSONObject();
				ob4.put("name", children.get(i).name);
				ob4.put("triamount", children.get(i).triposhaAmount);
				ob2.put("row"+i, ob4);
				ArrayList<String[]> s = children.get(i).vaccines;
				for(int j=0;j<s.size();j++){
					JSONObject ob3 = new JSONObject();
					ob3.put("name", children.get(i).name);
					ob3.put("age", children.get(i).age);
					ob3.put("vname", s.get(j)[1]);
					ob3.put("vamount", s.get(j)[2]);
					ob1.put("row"+i+""+j, ob3);
				}
			}
			ob.put("ob1", ob1);
			ob.put("ob2", ob2);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		out.print(ob);
	}
}
