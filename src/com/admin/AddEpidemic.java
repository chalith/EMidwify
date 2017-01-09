package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.JDBC;
@WebServlet("/addepidemic")
public class AddEpidemic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();
		String eps[] = request.getParameter("txtepidemics").split("~row~");
		try{
			for(int i=1;i<eps.length;i++){
				String ep = eps[i];
				String epidemic[] = ep.split("~column~");
				String q = "INSERT INTO epidemic (epidemicCode,epidemicName,description) VALUES ('"+epidemic[0].trim()+"','"+epidemic[1].trim()+"','"+epidemic[2].trim()+"');";
				jdbc.st.executeUpdate(q);
			}
			request.setAttribute("finalAlert","<script>showsuccessmessage(\"Epidemic added successfully\")</script>");
        	getServletContext().getRequestDispatcher("/admin/addepidemic.jsp").forward(request,response);
            out.println("successfully added");
		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	    	getServletContext().getRequestDispatcher("/admin/addepidemic.jsp").forward(request,response);
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
