package com.project.translate.Translator.excelops;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.project.translate.Translator.IbmFunctions.IbmData;
import com.project.translate.Translator.getListoflanguage.LanguageList;
import com.project.translate.Translator.jsonservice.JsonServices;

public class ExcelOps {
	
	private String[] langaugeArr;
	private String filePath;
	private IbmData ibm;
	private JsonServices json;
	
	public ExcelOps(String fileP,IbmData ibm, JsonServices json) {
		this.filePath=fileP;
		this.ibm= ibm;
		this.json = json;
	}
	
	private String getLanguageName(String code) {
		return LanguageList.languagecodes.get(code);
	}
	
	private String getEnglishText(String data) {
		JSONObject j = this.json.getJsonObject(data);
		JSONArray a = this.json.getJsonArray(j, "translations");
		return this.json.getKeyValue(a, 0, "translation");
	}
	
	
	public void addLanguagesToArray() throws IOException {
		FileInputStream fis = new FileInputStream(this.filePath);
		    	
		    	XSSFWorkbook w = new XSSFWorkbook(fis);
		    	XSSFSheet sheet = w.getSheetAt(0);
		    	
		    	System.out.println("loading excel data...");
		    	
		    	int rows = sheet.getLastRowNum();
		    	this.langaugeArr = new String[rows];
		    	
		    	for(int r=1;r<=rows;r++) {
		    		XSSFRow row = sheet.getRow(r);
		    		this.langaugeArr[r-1]=row.getCell(0).getStringCellValue();
		    	}
		    	
		    	fis.close();
		    	System.out.println("loading completed.");
	}
	
	public void translateLanguageAndStore() throws IOException {
		FileInputStream fis = new FileInputStream(this.filePath);
    	
    	XSSFWorkbook w = new XSSFWorkbook(fis);
    	XSSFSheet sheet = w.getSheetAt(0);
    	
    	System.out.println("translating and storing data...");
    	
    	int rows = sheet.getLastRowNum();
    	int cols = sheet.getRow(0).getLastCellNum();
    	
    	for(int r=1;r<=rows;r++) {
    		XSSFRow row = sheet.getRow(r);
    		System.out.println("translating -"+this.langaugeArr[r-1]);
    		String data="";
    		try {
    			data = this.ibm.translateToEnglish(this.langaugeArr[r-1]);
    		}catch(RuntimeException re) {
    			data = "not supported";
    		}
    		
    		for(int c=1;c<cols;c++) {
    			if(c==1) {
    				if(data.equals("not supported")) {
    					row.createCell(c).setCellValue(data);
    				}else {
    					String text = this.getEnglishText(data);
    					row.createCell(c).setCellValue(text);
    				}
    			}
    					
    			if(c==2) {
    				if(data.equals("not supported")) {
    					row.createCell(c).setCellValue(data);
    				}else {
    					String text = this.getLanguageName(this.json.getStringFromObject(data, "detected_language"));
    					row.createCell(c).setCellValue(text);
    				}
    				
    			}
    					
    		}
    	}
    	
    	fis.close();
    	
    	System.out.println("translation completed.");
    	
    	FileOutputStream os = new FileOutputStream(this.filePath);
    	w.write(os);
    	os.flush();
    	os.close();
    	
	}
	
	
	
	

}
