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
@WebServlet("/adddisease")
public class AddDisease extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc = new JDBC();
		String diss[] = request.getParameter("txtdiseases").split("~row~");
		try{
			for(int i=1;i<diss.length;i++){
				String dis = diss[i];
				String disease[] = dis.split("~column~");
				String q = "INSERT INTO disease (diseaseCode,diseaseName,description) VALUES ('"+disease[0].trim()+"','"+disease[1].trim()+"','"+disease[2].trim()+"');";
				jdbc.st.executeUpdate(q);
			}
			request.setAttribute("finalAlert","<script>showsuccessmessage(\"Disease added successfully\")</script>");
        	getServletContext().getRequestDispatcher("/admin/adddisease.jsp").forward(request,response);
            out.println("successfully added");
		}
		catch(Exception e){
			e.printStackTrace();
			request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	    	getServletContext().getRequestDispatcher("/admin/adddisease.jsp").forward(request,response);
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
