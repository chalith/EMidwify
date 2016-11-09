package com.midwife.viewmother;

import java.io.IOException;

import java.util.*;
import java.text.*;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Children;
import com.main.Date;
import com.mysql.jdbc.Util;
public class LoadChildren extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String gid = (String) request.getParameter("guardianid");
		String childbar ="";
	    
    	DateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date dt = new java.util.Date();
    	Date currentDate = createDate(frmt.format(dt),"-");
    	//out.println(areaCode);
    	Children children = new Children(gid);
    	ArrayList<String[]> childrenArr= children.getChildren();
    	childbar="<table>";
    	for(int i=0;i<childrenArr.size();i++){
    		Date date = createDate(childrenArr.get(i)[2],"-");
    		childbar = childbar + "<tr name=\"child\" id=\""+childrenArr.get(i)[0]+"\"><div class=\"baby\" name=\"child\" id=\""+childrenArr.get(i)[0]+"\"><img id=\""+childrenArr.get(i)[0]+"\" src=\""+childrenArr.get(i)[3]+"\" alt=\"user_photo\"><h1 style=\"color:gray;\" id=\""+childrenArr.get(i)[0]+"\">"+childrenArr.get(i)[1]+"</h1><a id=\""+childrenArr.get(i)[0]+"\" style=\"color:white;\">age :- ( "+date.getAge(currentDate)+" )</a></div></tr>";
        }
    	childbar=childbar + "</table>";
    	out.println(childbar);
        //System.out.println(childbar);
    }
	Date createDate(String date,String seperator){
		String d[] = new String[3];
		if(date.charAt(4)=='-'){
			 d = date.split("-");
		}
		else if(date.charAt(4)=='/'){
			d = date.split("/");
		}
		String year = d[0];
		String month = d[1];
		String day = d[2];
		Date da = null;
		try{
			da = new Date(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
		}catch(Exception e){
			System.out.print(e);
		}
		return da;
	}
}
