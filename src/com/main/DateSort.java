package com.main;

import java.util.ArrayList;

import org.json.JSONObject;

public class DateSort {
	public static ArrayList<Notification> sort(ArrayList<Notification> notifications){
		if(notifications.size()>1){
			int mid = notifications.size()/2;
			ArrayList<Notification> p1 = sort(new ArrayList<Notification>(notifications.subList(0, mid)));
			ArrayList<Notification> p2 = sort(new ArrayList<Notification>(notifications.subList(mid, notifications.size())));
			ArrayList<Notification> p = new ArrayList<Notification>();
			while((!p1.isEmpty())&&(!p2.isEmpty())){
				Date d1 = FormatDate.createDate(p1.get(0).date);
				Date d2 = FormatDate.createDate(p2.get(0).date);
				if(d1.isLarge(d2)){
					p.add(p2.get(0));
					p2.remove(0);
				}
				else{
					p.add(p1.get(0));
					p1.remove(0);
				}
			}
			while(!p1.isEmpty()){
				p.add(p1.get(0));
				p1.remove(0);
			}
			while(!p2.isEmpty()){
				p.add(p2.get(0));
				p2.remove(0);
			}
			return p;
		}
		return notifications;
	}
	public static ArrayList<JSONObject> sortWeight(ArrayList<JSONObject> weights){
		if(weights.size()>1){
			try{
				int mid = weights.size()/2;
				ArrayList<JSONObject> p1 = sortWeight(new ArrayList<JSONObject>(weights.subList(0, mid)));
				ArrayList<JSONObject> p2 = sortWeight(new ArrayList<JSONObject>(weights.subList(mid, weights.size())));
				ArrayList<JSONObject> p = new ArrayList<JSONObject>();
				while((!p1.isEmpty())&&(!p2.isEmpty())){
						Date d1 = FormatDate.createDate(p1.get(0).getString("date"));
						Date d2 = FormatDate.createDate(p2.get(0).getString("date"));
						if(d1.isLarge(d2)){
							p.add(p2.get(0));
							p2.remove(0);
						}
						else{
							p.add(p1.get(0));
							p1.remove(0);
						}
				}
				while(!p1.isEmpty()){
					p.add(p1.get(0));
					p1.remove(0);
				}
				while(!p2.isEmpty()){
					p.add(p2.get(0));
					p2.remove(0);
				}
				return p;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return weights;
	}
}
