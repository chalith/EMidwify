package com.midwife.profile;

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
import javax.servlet.http.HttpSession;

import com.main.Children;
import com.main.Guardians;
import com.main.JDBC;
import com.main.Search;
public class LoadChildSuggestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();;
		String name = (String) request.getParameter("cname");
		String childr = "";
		HttpSession session = request.getSession(false);
		if((session != null) && (request.isRequestedSessionIdValid())){
			String mid = (String) session.getAttribute("mid");
			try{
				String q = "SELECT areaCode FROM area WHERE midwifeID = '"+mid+"';";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				while (rs.next()) {
					Children children = new Children(rs.getString("areaCode"),"");
				    ArrayList<String[]> childrenArr = children.getChildren();
			    	for(int i=0;i<childrenArr.size();i++){
			    		String s[] = childrenArr.get(i);
			    		if(Search.isSuggestion(s[1] , name))
			    			childr = childr + "<div class=\"result\" id=\""+s[0]+"\"><img id=\""+s[0]+"\" src=\""+s[2]+"\" alt=\"user_photo\"><a id=\""+s[0]+"\">"+s[1]+"</a></div>";
			        }
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			out.print(childr);
		}
	}
}
