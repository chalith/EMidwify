package com.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;
@WebServlet("/setimage")
public class SetImage extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if((session!=null)&&(request.isRequestedSessionIdValid())){
			String id = (String)session.getAttribute("mid");
			String usertype = "";
			String folder = "";
			Main m = new Main();
			if(m.isHave("guardian", "guardianID", id)){
				usertype = "guardian";
				folder = "mother";
			}
			else if(m.isHave("midwife", "midwifeID", id)){
				usertype = "midwife";
				folder = "midwife";
			}
			else if(m.isHave("supervisor", "supervisorID", id)){
				usertype = "supervisor";
				folder = "midwife";
			}
			Part filepart = request.getPart("txtsourcepath");
			if(filepart!=null){
				String extention = filepart.getContentType();
				extention = extention.substring(extention.lastIndexOf("/")+1);
				String destination = folder+"/images/"+id+"."+extention;
				if(extention.equals("png")||extention.equals("jpeg")||extention.equals("jpg")){
					JDBC jdbc = new JDBC();
					try{
						String q = "UPDATE "+usertype+" SET "+usertype+"Picture = '"+destination+"' WHERE "+usertype+"ID = '"+id+"';";
						jdbc.st.executeUpdate(q);
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try {
							jdbc.conn.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else{
					request.setAttribute("warning", "<script>showalert(\"Invalid file type\")</script>");
					getServletContext().getRequestDispatcher("/pictureupload.jsp").forward(request, response);
				}
			}else{
				request.setAttribute("warning", "<script>showalert(\"The picture does not exist\")</script>");
				getServletContext().getRequestDispatcher("/pictureupload.jsp").forward(request, response);
			}
		}
	}
	private String getExtenstion(File file){
		String name = file.getName();
		int i = name.lastIndexOf(".");
		if((i!=0)&&(i!=-1)){
			return name.substring(i+1);
		}
		else{
			return "";
		}
	}
	private String convertPath(String s){
		String s2="";
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='\\'){
				s2+="/";
			}
			else{
				s2+=s.charAt(i);
			}
		}
		return s2;
	}
}
