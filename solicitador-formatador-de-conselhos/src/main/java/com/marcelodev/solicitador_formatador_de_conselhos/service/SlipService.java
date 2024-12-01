package com.marcelodev.solicitador_formatador_de_conselhos.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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

		} catch (RestClientException e) {
			System.err.println("Falha na requisição HTTP: " + e.getMessage());
			return null;
		} catch (NullPointerException e) {
			System.err.println("Resposta da API foi nula: " + e.getMessage());
			return null;
		} catch (IllegalStateException e) {
			System.err.println("valor inesperado da API: " + e.getMessage());
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
		try {
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

		} catch (IllegalArgumentException e) {
			System.err.println("Número invalido! " + e.getMessage());
			return null;

		}
	}

	// Método para retornar todos os conselhos carregados
	public List<Slip> getTodosSlips() {
		return new ArrayList<>(slips);
	}

	public void salvarEmArquivo(List<Slip> slips) {
		String caminhoArquivo = "slips.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) { // 'true' para não
																									// sobrescrever
			for (Slip slip : slips) {
				writer.write(slip.getId() + " " + slip.getAdvice());
				writer.newLine();
			}
			System.out.println("Slips adicionados ao arquivo com sucesso em: " + caminhoArquivo);
		} catch (IOException e) {
			System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
		}
	}

	public void salvarTodosSlipsEmArquivo() {
		// Busca todos os slips disponíveis
		List<Slip> todosSlips = getTodosSlips(); // Este método deve retornar todos os slips do sistema

		// Salva os slips no arquivo de texto
		salvarEmArquivo(todosSlips); // Reutiliza o método salvarEmArquivo já implementado
	}

	public List<Slip> carregarSlipsDoArquivo() throws FileNotFoundException {
		String caminhoArquivo = "slips.txt";
		List<Slip> slips = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] partes = linha.split(" ", 2); // Divide em ID e Conselho
				if (partes.length == 2) {
					try {
						Slip slip = new Slip();
						slip.setId(Long.parseLong(partes[0])); // Converte ID para Long
						slip.setAdvice(partes[1]); // O restante da linha é o conselho
						slips.add(slip);
					} catch (NumberFormatException e) {
						System.err.println("Erro ao converter ID: " + partes[0]);
					}
				}
			}
			System.out.println("Slips carregados do arquivo com sucesso.");
		} catch (IOException e) {
			System.err.println("Erro ao carregar o arquivo: " + e.getMessage());
		}

		return slips;
	}

}
