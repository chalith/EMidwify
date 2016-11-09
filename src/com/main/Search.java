package com.main;

public class Search {
	public static boolean isSuggestion(String name, String input){
		if((name.toLowerCase()).startsWith(input.toLowerCase())){
			return true;
		}
		else{
			String s1[] = name.split(" ");
			for(String x:s1){
				if((x.toLowerCase()).startsWith(input.toLowerCase())){
					return true;
				}
				else{
					String s2[] = x.split("\\.");
					for(String y:s2){
						if((y.toLowerCase()).startsWith(input.toLowerCase())){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
