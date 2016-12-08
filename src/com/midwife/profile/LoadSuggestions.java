package com.midwife.profile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.Children;
import com.main.Guardians;
import com.main.JDBC;
import com.main.Main;
import com.main.Search;
@WebServlet("/loadsuggestions")
public class LoadSuggestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();
		Main m=new Main();
		String name = (String) request.getParameter("name");
		String childr = "";
		String guardian = "";
		HttpSession session = request.getSession(false);
		if((session != null) && (request.isRequestedSessionIdValid())){
			String mid = (String) session.getAttribute("mid");
			try{
				String q="";
				if(m.getPerson(mid)=="midwife"){
					q = "SELECT areaCode FROM area WHERE midwifeID = '"+mid+"';";
				}
				else if(m.getPerson(mid)=="supervisor"){
					q = "SELECT areaCode FROM area WHERE midwifeID IN (SELECT midwifeID FROM midwife WHERE supervisorID = '"+mid+"');";
				}
				jdbc.st.executeQuery(q);
				ResultSet rs = jdbc.st.getResultSet();
				while (rs.next()) {
					Children children = new Children(rs.getString("areaCode"),"");
				    ArrayList<String[]> childrenArr = children.getChildren();
				    for(int i=0;i<childrenArr.size();i++){
			    		String s[] = childrenArr.get(i);
			    		if(Search.isSuggestion(s[1] , name))
			    			childr = childr + "<div class=\"result\" id=\""+s[0]+"\"><img id=\""+s[0]+"\" src=\""+s[2]+"\" alt=\"user_photo\"><a id=\""+s[0]+"\">"+s[1]+"</a></div>";
			        }
			    	Guardians guardians = new Guardians(rs.getString("areaCode"));
				    ArrayList<String[]> guardianArr = guardians.getGuardians();
			    	for(int i=0;i<guardianArr.size();i++){
			    		String s[] = guardianArr.get(i);
			    		if(Search.isSuggestion(s[1] , name))
			    			guardian = guardian + "<div class=\"result\" id=\""+s[0]+"\"><img id=\""+s[0]+"\" src=\""+s[2]+"\" alt=\"user_photo\"><a id=\""+s[0]+"\">"+s[1]+"    ( "+s[3]+" )</a></div>";
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
			out.print("<div id=\"childrenresults\">"+childr+"</div><div id=\"motherresults\">"+guardian+"</div>");
		}
		
	}
}
