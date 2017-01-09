package com.midwife.child;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.JDBC;
import com.main.Main;
public class LoadMother extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String deathmotherid = request.getParameter("txtdeathmotherid");
		HttpSession session = request.getSession();
		String midwifeid = (String) session.getAttribute("mid");
		String data = null;
		Main m = new Main();
		if(m.isHave("mother", "guardianID", deathmotherid)){
			JDBC jdbc = new JDBC();
			try{
				String q = "SELECT guardianName,guardianAreacode FROM guardian WHERE (guardianID = '"+deathmotherid+"') && (guardianAreaCode IN (SELECT areaCode FROM area WHERE midwifeID = '"+midwifeid+"'));";
				Statement st=jdbc.conn.createStatement();
				st.executeQuery(q);
				ResultSet rs = st.getResultSet();
				if(rs.next()){
					data = rs.getString("guardianName") + "~column~" + rs.getString("guardianAreaCode");
				}
				out.print(data);
				//System.out.println(data);
			}catch(Exception e){
				System.out.println(e);
			}finally{
				try {
					jdbc.conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
