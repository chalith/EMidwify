package com.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

import org.apache.catalina.Session;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.nio.file.Files;
@MultipartConfig(maxFileSize=169999999)
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if((session!=null)&&(request.isRequestedSessionIdValid())){
			Main m = new Main();
			String id = (String)session.getAttribute("mid");
			String usertype = m.getPerson(id);
			String folder = "";
			if(usertype == "guardian"){
				folder = "mother";
			}
			else if(usertype == "midwife"){
				folder = "midwife";
			}
			else if(usertype == "supervisor"){
				folder = "supervisor";
			}
			else if(usertype == "admin"){
				folder = "admin";
			}
			Part filepart = request.getPart("txtsourcepath");
			if(filepart!=null){
				String extention = filepart.getContentType();
				extention = extention.substring(extention.lastIndexOf("/")+1);
				if(extention.equals("png")||extention.equals("jpeg")||extention.equals("jpg")){
					String server = getServletContext().getRealPath("EMidwify");
					server = server.substring(0,server.lastIndexOf("\\"));
					String destination = folder+"/images/"+id+"."+extention;
					File file2 = new File(server+"/"+destination);
					InputStream is = filepart.getInputStream();
					if(file2.exists()){
						Files.delete(file2.toPath());
					}
					OutputStream os = new FileOutputStream(server+"/"+destination);
					IOUtils.copy(is, os);
					is.close();
					os.close();
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
				}
			}else{
				request.setAttribute("warning", "<script>showalert(\"The picture does not exist\")</script>");
			}
			response.sendRedirect(request.getHeader("referer"));
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
