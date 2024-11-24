package com.marcelodev.solicitador_formatador_de_conselhos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcelodev.solicitador_formatador_de_conselhos.entidade.Slip;
import com.marcelodev.solicitador_formatador_de_conselhos.service.SlipService;

@RestController
@RequestMapping("/api/slips")
public class SlipController {

	@Autowired
	private SlipService slipService;

	@GetMapping("/carregarSlips/{numeroConselhos}")
	public ResponseEntity<List<Slip>> carregarSlips(@PathVariable int numeroConselhos) {
		return ResponseEntity.ok().body(slipService.carregarSlips(numeroConselhos));
	}

	@GetMapping
	public ResponseEntity<List<Slip>> gettodosSlips() {
		return ResponseEntity.ok().body(slipService.getTodosSlips());

	}
	
	@PostMapping("/salvarTexto")
	public ResponseEntity<String> salvarTextoSlips() {
	    try {
	        // Chama o método no service que salva todos os slips no arquivo
	        slipService.salvarTodosSlipsEmArquivo();
	        return ResponseEntity.ok("Todos os slips foram salvos no arquivo com sucesso!");
	    } catch (Exception e) {
	        // Retorna erro com detalhes caso ocorra alguma exceção
	        return ResponseEntity.status(500).body("Erro ao salvar os slips no arquivo: " + e.getMessage());
	    }
	}
	
	@GetMapping("/carregarDoArquivo")
	public ResponseEntity<List<Slip>> carregarSlipsDoArquivo() {
	    try {
	        // Chama o método do service para carregar os slips do arquivo
	        List<Slip> slips = slipService.carregarSlipsDoArquivo();
	        return ResponseEntity.ok(slips); // Retorna a lista de slips em formato JSON
	    } catch (Exception e) {
	        // Retorna erro caso algo dê errado
	        return ResponseEntity.status(500).body(null);
	    }
	}




}
