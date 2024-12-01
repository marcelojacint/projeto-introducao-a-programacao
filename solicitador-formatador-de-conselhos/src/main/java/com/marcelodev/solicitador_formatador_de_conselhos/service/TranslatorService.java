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

		try {
			Translate translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();

			Translation translation = translate.translate(textToTranslate,
					Translate.TranslateOption.sourceLanguage("en"), Translate.TranslateOption.targetLanguage("pt"));

			return translation.getTranslatedText();
			
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Erro nos parâmetros de tradução: " + e.getMessage(), e);
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao traduzir texto com a API do Google Translate: " + e.getMessage(), e);
		}
	}

}
