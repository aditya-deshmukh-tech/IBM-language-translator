package com.project.translate.Translator.getListoflanguage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.project.translate.Translator.IbmFunctions.IbmData;
import com.project.translate.Translator.jsonservice.JsonServices;

public class LanguageList extends Thread{
	
	public static boolean flag=false;
	private IbmData ibm;
	private JsonServices json;
	
	public static HashMap<String, String> languagecodes = new HashMap<String, String>();
	
	public LanguageList(IbmData ibm, JsonServices json) {
		this.ibm = ibm;
		this.json = json;
	}
	
	public void run() {
		synchronized (this) {
			this.getLanguages();
			this.flag = true;
			try {
				Thread.sleep(50);
			}catch(Exception e) {
				e.printStackTrace();
			}
			this.notify();
		}
	}
	
	public void getLanguages() {
		System.out.println("getting list of languages...");
		String data = this.ibm.getListOfLanguages();
		JSONObject o = this.json.getJsonObject(data);
		JSONArray a = this.json.getJsonArray(o, "languages");
		
		for(int i=0;i<a.length();i++) {
			this.languagecodes.put(this.json.getKeyValue(a, i, "language"), this.json.getKeyValue(a, i, "language_name"));
		}
	}
	
	
}
