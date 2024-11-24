package com.marcelodev.solicitador_formatador_de_conselhos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelodev.solicitador_formatador_de_conselhos.entidade.Slip;
import com.marcelodev.solicitador_formatador_de_conselhos.service.SlipService;

@RestController
@RequestMapping("/api/slips")
public class SlipController {

	@Autowired
	private SlipService slipService;

	@GetMapping("/carregarSlips/{numero_Conselhos}")
	public ResponseEntity<List<Slip>> carregarSlips(@PathVariable int numero_Conselhos) {
		return ResponseEntity.ok().body(slipService.carregarSlips(numero_Conselhos));
	}

	@GetMapping
	public ResponseEntity<List<Slip>> gettodosSlips() {
		return ResponseEntity.ok().body(slipService.getTodosSlips());

	}

}
