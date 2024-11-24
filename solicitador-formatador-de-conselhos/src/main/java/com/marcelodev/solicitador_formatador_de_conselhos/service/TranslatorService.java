package com.marcelodev.solicitador_formatador_de_conselhos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Service
public class TranslatorService {

	@Value("${google.api.key}")
	private String apiKey;

	private Translate translate;

	public TranslatorService() {
		this.translate = TranslateOptions.getDefaultInstance().getService();

	}

	public String translateToPortuguese(String textToTranslate) {

		Translate translate = TranslateOptions.newBuilder().setApiKey(apiKey) 
				.build().getService();

		Translation translation = translate.translate(textToTranslate, Translate.TranslateOption.sourceLanguage("en"),
				Translate.TranslateOption.targetLanguage("pt"));

		return translation.getTranslatedText();
	}

}
