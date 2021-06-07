package com.project.translate.Translator;

import com.project.translate.Translator.IbmFunctions.IbmData;
import com.project.translate.Translator.excelops.ExcelOps;
import com.project.translate.Translator.getListoflanguage.LanguageList;
import com.project.translate.Translator.jsonservice.JsonServices;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception 
    {
    	String file = args[0].replace("\\", "/");
    	IbmData ibm = new IbmData();
    	JsonServices json = new JsonServices();
    	ExcelOps e = new ExcelOps(file,ibm,json);
    	LanguageList list = new LanguageList(ibm, json);
    	list.start();
    	
    	e.addLanguagesToArray();
    	
    	synchronized (list) {
			
    		if(!LanguageList.flag) {
    			list.wait();
    		}
    		e.translateLanguageAndStore();
		}
    	
    }
}
