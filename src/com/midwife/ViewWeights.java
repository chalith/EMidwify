package com.midwife;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.main.JDBC;

@WebServlet("/viewweights")
public class ViewWeights extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String person = request.getParameter("person");
		String id = request.getParameter("id");
		GetWeights gw = new GetWeights(person, id);
		ArrayList<JSONObject> weights = gw.getWeights();
		JSONObject output = new JSONObject();
		try{
			for(int i=0;i<weights.size();i++){
				output.put(Integer.toString(i), weights.get(i));
				//System.out.println(weights.get(i).getString("date")+" "+weights.get(i).getString("age")+" "+weights.get(i).getString("weight"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		out.print(output);
	}
}
