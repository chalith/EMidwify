package com.main;
import java.util.*;
public class Mother extends Guardian {
	public ArrayList<String[]> epidemics = new ArrayList<String[]>();
	public ArrayList<String[]> abortions = new ArrayList<String[]>();
	public ArrayList<String[]> childdeaths = new ArrayList<String[]>();
	public void addEpidemic(String epCode,String  epName,String  epDate,String  epNote){
		String[] epidemic = {epCode, epName, epDate, epNote};
		epidemics.add(epidemic);
	}
	public void addAbortion(String  abDate,String  abNote){
		String[] abortion = {abDate, abNote};
		abortions.add(abortion);
	}
	public void addChilddeath(String  cdName,String  cdDate,String  cdReason){
		String[] childdeath = {cdName, cdDate, cdReason};
		childdeaths.add(childdeath);
	}
}
