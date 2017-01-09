package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.JDBC;
@WebServlet("/addvaccine")
public class AddVaccine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();
		String vcs[] = request.getParameter("txtvaccines").split("~row~");
		try{
			for(int i=1;i<vcs.length;i++){
				String vc = vcs[i];
				String vaccine[] = vc.split("~column~");
				String q = "INSERT INTO vaccine (vaccineCode,vaccineName,description) VALUES ('"+vaccine[0].trim()+"','"+vaccine[1].trim()+"','"+vaccine[2].trim()+"');";
				Statement st=jdbc.conn.createStatement();
				st.executeUpdate(q);
			}
			request.setAttribute("finalAlert","<script>showsuccessmessage(\"Vaccine added successfully\")</script>");
        	getServletContext().getRequestDispatcher("/admin/addvaccine.jsp").forward(request,response);
            out.println("successfully added");
		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	    	getServletContext().getRequestDispatcher("/admin/addvaccine.jsp").forward(request,response);
		}
		finally{
			try {
				jdbc.conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
