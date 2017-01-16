package com.supervisor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.main.ChildInClinic;
import com.main.ClinicDates;
import com.main.MotherInClinic;
import com.midwife.ClinicUnvisited;
import com.midwife.ClinicVisited;

public class GeneratePDF {


	private static Font TIME_ROMAN = 
			new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = 
			new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);


	/**
	 * @param args
	 */
	public static Document createPDF(String area,String date ,String file) {
		
	Document document = null;

	try {
		document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();

		addMetaData(document);

		addTitlePage(document);
		
		createTable(area,date,document);
		
		document.close();

	} catch (Exception  e) {
		
		e.printStackTrace();
	}
	return document;

	}

	private static void addMetaData(Document document) {
		document.addTitle("Supervisor Report");
		document.addSubject("Supervisor Report");
		
	}

	private static void addTitlePage(Document document)
			throws DocumentException {
		
		Paragraph preface = new Paragraph();
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("EMidwify Report of Triposha", TIME_ROMAN));

		creteEmptyLine(preface, 1);
		SimpleDateFormat simpleDateFormat = 
				new SimpleDateFormat("MM/dd/yyyy");
		preface.add(new Paragraph(
				"Report created on "+ simpleDateFormat
				.format(new Date()),TIME_ROMAN_SMALL));
		document.add(preface);
		
		
	}

	private static void creteEmptyLine(Paragraph paragraph, 
			int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	private static void createTable(String area ,String date,Document document) 
			throws DocumentException {
		
		
	
		ClinicVisited clinic = new ClinicVisited(area, date);
		ClinicUnvisited uclinic = new ClinicUnvisited(area, date);
		ArrayList<MotherInClinic> mothers = clinic.getVisitedMothers();
		ArrayList<ChildInClinic> children = clinic.getVisitedChildren();
		int unvisitedcount = uclinic.getUnvisitedMotherCount();
		ClinicDates cDates = new ClinicDates(area);
		String venuetime[] = cDates.getPastDeatils(date);
		
		
		try{
			Paragraph paragraph = new Paragraph();
			creteEmptyLine(paragraph, 1);
			paragraph.add("Triposha report of Mother");
			
			creteEmptyLine(paragraph, 1);
			document.add(paragraph);
			PdfPTable table = new PdfPTable(2);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Mother Name"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			PdfPCell c2 = new PdfPCell(new Phrase("Amount"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c2);
			
			
			
			for(int i=0;i<mothers.size();i++){
				JSONObject ob4 = new JSONObject();
				ob4.put("name", mothers.get(i).name);
				ob4.put("triamount", mothers.get(i).triposhaAmount);
				//System.out.println(mothers.get(i).name);
				//System.out.println( mothers.get(i).triposhaAmount);
				
					table.setWidthPercentage(100);
					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
					table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(mothers.get(i).name);
					table.addCell(Integer.toString(mothers.get(i).triposhaAmount));
					
					
					
				}
			
			document.add(table);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	

	try{
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 1);
		paragraph.add("Triposha report of children");

		
		creteEmptyLine(paragraph, 1);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(2);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Child Name"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		PdfPCell c2 = new PdfPCell(new Phrase("Amount"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c2);
		
		
		
		for(int i=0;i<mothers.size();i++){
			JSONObject ob4 = new JSONObject();
			ob4.put("name", children.get(i).name);
			ob4.put("triamount", children.get(i).triposhaAmount);
			//System.out.println(children.get(i).name);
			//System.out.println( children.get(i).triposhaAmount);
			
				table.setWidthPercentage(100);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(children.get(i).name);
				table.addCell(Integer.toString(children.get(i).triposhaAmount));
				
				
				
			}
		
		document.add(table);
		
	}
	catch(Exception e){
		e.printStackTrace();
	}

		
	
	
	try{
		
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 1);
		paragraph.add("Vaccine report of Children");
		creteEmptyLine(paragraph, 1);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(4);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Child Name"));
		//c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		PdfPCell c2 = new PdfPCell(new Phrase("Age"));
		//c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c2);
		
		PdfPCell c3 = new PdfPCell(new Phrase("Vaccine"));
		//c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c3);
		
		PdfPCell c4 = new PdfPCell(new Phrase("Amount"));
		//c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c4);
		
		
		
		for(int i=0;i<children.size();i++){
			JSONObject ob4 = new JSONObject();
			
			//System.out.println(s.get(i)[1]);
			//System.out.println(s.get(i)[2]);
			
			ArrayList<String[]> s = children.get(i).vaccines;
			for(int j=0;j<s.size();j++){
				table.setWidthPercentage(100);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				//System.out.println(s.get(j)[1]);
				//System.out.println(s.get(j)[2]);
				table.addCell(children.get(i).name);
				table.addCell((children.get(i).age));
				table.addCell(s.get(j)[1]);
				table.addCell(s.get(j)[2]);
			}
		}
		
		document.add(table);
		
	}
	catch(Exception e){
		e.printStackTrace();
	}

	
/*try{
		
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 1);
		paragraph.add("Vaccine report of Children");
		creteEmptyLine(paragraph, 1);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(4);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Child"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		PdfPCell c2 = new PdfPCell(new Phrase("Age"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c2);
		
		PdfPCell c3 = new PdfPCell(new Phrase("Vaccine"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c3);
		
		PdfPCell c4 = new PdfPCell(new Phrase("Amount"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c4);
		
		
		
		for(int i=0;i<children.size();i++){
			JSONObject ob4 = new JSONObject();
			
			//System.out.println(s.get(i)[1]);
			//System.out.println(s.get(i)[2]);
			
			ArrayList<String[]> s = children.get(i).vaccines;
			for(int j=0;j<s.size();j++){
				table.setWidthPercentage(100);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				//System.out.println(s.get(j)[1]);
				//System.out.println(s.get(j)[2]);
				table.addCell(children.get(i).name);
				table.addCell((children.get(i).age));
				table.addCell(s.get(j)[1]);
				table.addCell(s.get(j)[2]);
			}
		}
		
		document.add(table);
		
	}
	catch(Exception e){
		e.printStackTrace();
	}*/

		
		
		
		
	}

}
