package com.midwife.motherupdate;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Date;
import com.main.JDBC;
public class UpdateMother extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JDBC jdbc=new JDBC();
		String id = request.getParameter("txtmotherid");
		String weight = request.getParameter("txtmotherweight");
		String clinicDate = request.getParameter("txtclinicdate");
		String bDate = "";
		try{
			String q="SELECT guardianBDate FROM guardian WHERE guardianID = '"+id+"';";
			jdbc.st.executeQuery(q);
			ResultSet rs = jdbc.st.getResultSet();
			while(rs.next()){
				bDate = (String)rs.getString("guardianBDate");
			}
		}catch(Exception e){
			System.out.print(e);
		}
    	Date clDate = createDate(clinicDate,"/");
    	Date birthDate = createDate(bDate,"-");
    	String age = birthDate.getAge(clDate);
		
		java.util.Date currentDate = new java.util.Date();
		DateFormat frmt = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
		String cDate = frmt.format(currentDate);
		
		ArrayList<String[]> epidemics = new ArrayList<String[]>();
		ArrayList<String[]> diseases = new ArrayList<String[]>();
		ArrayList<String[]> vaccines = new ArrayList<String[]>();
		ArrayList<String[]> childDeaths = new ArrayList<String[]>();
		ArrayList<String[]> triposhas = new ArrayList<String[]>();

		String eps[] = request.getParameter("txtepidemic").split("~row~");
		for(int i=1;i<eps.length;i++){
			String ep = eps[i];
			String epidemic[] = ep.split("~column~");
			String temp[] = {epidemic[0].trim(),epidemic[1].trim(),epidemic[2].trim(),epidemic[3].trim()};
			epidemics.add(temp);
		}
		String dss[] = request.getParameter("txtdisease").split("~row~");
		for(int i=1;i<dss.length;i++){
			String ds = dss[i];
			String disease[] = ds.split("~column~");
			String temp[] = {disease[0].trim(),disease[1].trim(),disease[2].trim(),disease[3].trim()};
			diseases.add(temp);
		}
		String vcs[] = request.getParameter("txtvaccine").split("~row~");
		for(int i=1;i<vcs.length;i++){
			String vc = vcs[i];
			String vaccine[] = vc.split("~column~");
			String temp[] = {vaccine[0].trim(),vaccine[1].trim(),vaccine[2].trim(),clinicDate};
			vaccines.add(temp);
		}
		String cds[] = request.getParameter("txtchilddeath").split("~row~");
		for(int i=1;i<cds.length;i++){
			String cd = cds[i];
			String childdeath[] = cd.split("~column~");
			String temp[] = {childdeath[0].trim(),childdeath[1].trim(),childdeath[2].trim(),childdeath[3].trim()};
			childDeaths.add(temp);
		}
		String tps[] = request.getParameter("txttriposha").split("~row~");
		for(int i=1;i<tps.length;i++){
			String tp = tps[i];
			String triposha[] = tp.split("~column~");
			String temp[] = {triposha[0].trim(),clinicDate};
			triposhas.add(temp);
		}
		if(!id.equals("")){		
	        try{
		        String q1="INSERT INTO motherclinic (motherID,clinicDate,age,weight,updatedDate) VALUES('"+id+"','"+clinicDate+"','"+age+"','"+weight+"','"+cDate+"')";
		        jdbc.st.executeUpdate(q1);
	            
            	for(int i=0;i<epidemics.size();i++){
		            String q2="INSERT INTO motherepidemics (motherID,epidemicCode,epidemicName,date,note) VALUES('"+id+"','"+epidemics.get(i)[0]+"','"+epidemics.get(i)[1]+"','"+epidemics.get(i)[2]+"','"+epidemics.get(i)[3]+"')";
		            jdbc.st.executeUpdate(q2);
	            }
	            for(int i=0;i<diseases.size();i++){
		            String q2="INSERT INTO motherdiseases (motherID,diseaseCode,diseaseName,diseaseDate,diseasenote) VALUES('"+id+"','"+diseases.get(i)[0]+"','"+diseases.get(i)[1]+"','"+diseases.get(i)[2]+"','"+diseases.get(i)[3]+"')";
		            jdbc.st.executeUpdate(q2);
		            //out.println("ab");
	            }
	            for(int i=0;i<vaccines.size();i++){
		            String q2="INSERT INTO mothergivenvaccines (motherID,vaccineCode,vaccineName,vaccineAmount,clinicDate) VALUES('"+id+"','"+vaccines.get(i)[0]+"','"+vaccines.get(i)[1]+"','"+vaccines.get(i)[2]+"','"+vaccines.get(i)[3]+"')";
		            jdbc.st.executeUpdate(q2);
		            //out.println("ab");
	            }
	            for(int i=0;i<childDeaths.size();i++){
		            String q2="INSERT INTO childdeath (motherID,childID,date,reason) VALUES('"+id+"','"+childDeaths.get(i)[1]+"','"+childDeaths.get(i)[2]+"','"+childDeaths.get(i)[3]+"')";
		            jdbc.st.executeUpdate(q2);
		            //out.println("cd");
	            }
	            for(int i=0;i<triposhas.size();i++){
		            String q2="INSERT INTO triposha (id,amount,date) VALUES('"+id+"','"+triposhas.get(i)[0]+"','"+triposhas.get(i)[1]+"')";
		            jdbc.st.executeUpdate(q2);
		            //out.println("cd");
	            }
	        	request.setAttribute("finalAlert","<script>showsuccessmessage(\"Clinic details updated successfully\")</script>");
	            out.println("successfully added");
	        }catch(Exception e){
	        	System.out.print(e);
	        	request.setAttribute("finalAlert","<script>showalert(\"Error occured\")</script>");
	        }
		}
		try {
			jdbc.conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/midwife/motherupdate.jsp").forward(request,response);
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
