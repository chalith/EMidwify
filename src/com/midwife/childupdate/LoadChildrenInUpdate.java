package com.midwife.childupdate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.main.Children;
import com.main.JDBC;
import com.main.Main;
public class LoadChildrenInUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String gid = request.getParameter("gid");
	    String userBar ="<option disabled selected value>Child Name</option>";
	    JDBC jdbc = new JDBC();;
    	Children children = new Children(gid);
    	ArrayList<String[]> childrenArr = children.getChildren();
    	for(int i=0;i<childrenArr.size();i++){
    		String cid = childrenArr.get(i)[0];
    		String name = childrenArr.get(i)[1];
    		userBar = userBar + "<option id=\""+cid+"\" name=\"mother\">"+name+" ( "+cid+" )</option>";
        }
    	
    	JSONObject ob = new JSONObject();
		try {
			ob.put("userbar",userBar);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Main m = new Main();
		try{
			if(m.isHave("mother", "guardianID", gid)){
				String q = "SELECT guardianName FROM guardian WHERE guardianID='"+gid+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
		        ResultSet rs2 = st.getResultSet();
		    	while(rs2.next()){
		    		ob.put("name", rs2.getString("guardianName"));
		    	}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
        out.println(ob);
        //System.out.println(userBar);
	}
}
