package com.project.translate.Translator.jsonservice;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonServices {
	
	public JSONObject getJsonObject(String json) {
		 return new JSONObject(json);
	}
	
	public JSONArray getJsonArray(JSONObject o,String key) {
		return o.getJSONArray(key);
	}
	
	public String getKeyValue(JSONArray a, int index, String key) {
		return a.getJSONObject(index).getString(key);
	}
	
	public String getStringFromObject(String data, String key) {
		return new JSONObject(data).getString(key);
	}

}
