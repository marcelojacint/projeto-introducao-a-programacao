package com.marcelodev.solicitador_formatador_de_conselhos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelodev.solicitador_formatador_de_conselhos.entidade.Slip;

@Service
public class SlipService {

	private final List<Slip> slips = new ArrayList<>();
	private final RestTemplate restTemplate = new RestTemplate();
	private final String ADVICE_API_URL = "https://api.adviceslip.com/advice";
	
	@Autowired
	private TranslatorService translatorService;

	private Slip carregarDadosSlip() {
		// Faz a requisição para a API externa e trata erros
		String resposta = null;
		try {
			resposta = restTemplate.getForObject(ADVICE_API_URL, String.class);

		} catch (Exception e) {
			System.err.println("Erro ao chamar a API: " + e.getMessage());
			return null;
		}

		if (resposta == null) {
			System.err.println("Resposta da API foi nula.");
			return null;
		}

		// Verifica se a resposta é HTML ou JSON
		if (resposta.startsWith("<html>")) {
			System.err.println("A API retornou uma página HTML, o que pode indicar um erro.");
			return null;
		}

		// Converte a resposta em JSON
		JsonNode respostaJson = null;
		try {
			respostaJson = new ObjectMapper().readTree(resposta);
			
		} catch (Exception e) {
			System.err.println("Erro ao converter a resposta para JSON: " + e.getMessage());
			return null;
		}

		// Verifica se o 'slip' existe
		JsonNode slipNode = respostaJson.get("slip");
		if (slipNode == null) {
			System.err.println("O  'slip' não foi encontrado na resposta.");
			return null;
		}

		// Extrai os dados do conselho
		Long id = slipNode.get("id").asLong();
		String advice = slipNode.get("advice").asText();
		
		String translatedAdvice = translatorService.translateToPortuguese(advice);

		// Cria o objeto Slip e adiciona à lista
		Slip slip = new Slip(id, translatedAdvice);
		slips.add(slip);

		return slip;
	}

	// Método para carregar múltiplos conselhos
	public List<Slip> carregarSlips(int numero_conselhos) {
		List<Slip> listaSlips = new ArrayList<>();
		for (int i = 0; i < numero_conselhos; i++) {
			Slip slip = carregarDadosSlip();
			if (slip != null) {
				listaSlips.add(slip);
			} else {
				// Caso o conselho não seja carregado, interrompe a coleta
				System.err.println("Falha ao carregar o conselho #" + (i + 1));
				break;
			}
		}

		return listaSlips;
	}

	// Método para retornar todos os conselhos carregados
	public List<Slip> getTodosSlips() {
		return new ArrayList<>(slips);
	}

}
