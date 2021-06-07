package com.project.translate.Translator.IbmFunctions;

import com.ibm.cloud.sdk.core.http.HttpConfigOptions;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.IdentifiedLanguages;
import com.ibm.watson.language_translator.v3.model.IdentifyOptions;
import com.ibm.watson.language_translator.v3.model.Languages;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

public class IbmData {
	
	private static final String apikey="sYbtA1NFhgVHaKiY3_30s511yc10ij9juVa959xRnjbM";
	private static final String url="https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/361e91e5-6ed1-4a2b-874e-e7870783dcb8";
	private static final String version = "2018-05-01";
	
	
	public String getListOfLanguages() {
		IamAuthenticator authenticator = new IamAuthenticator(this.apikey);
		LanguageTranslator languageTranslator = new LanguageTranslator(this.version, authenticator);
		languageTranslator.setServiceUrl(this.url);

		HttpConfigOptions configOptions = new HttpConfigOptions.Builder()
		  .disableSslVerification(true)
		  .build();
		languageTranslator.configureClient(configOptions);
		Languages languages = languageTranslator.listLanguages()
				  .execute().getResult();
		return languages.toString();
	}
	
	public String translateToEnglish(String text) {
		IamAuthenticator authenticator = new IamAuthenticator(this.apikey);
		LanguageTranslator languageTranslator = new LanguageTranslator(this.version, authenticator);
		languageTranslator.setServiceUrl(this.url);

		HttpConfigOptions configOptions = new HttpConfigOptions.Builder()
		  .disableSslVerification(true)
		  .build();
		languageTranslator.configureClient(configOptions);
		TranslateOptions translateOptions = new TranslateOptions.Builder()
				  .addText(text)
				  .target("en")
				  .build();

				TranslationResult result = languageTranslator.translate(translateOptions)
				  .execute().getResult();
			return result.toString();
	}
	
	

}
