package com.marcelodev.solicitador_formatador_de_conselhos.entidade;


public class Slip {
	
	private Long id;
	private String advice;
	
	public Slip() {
	}

	public Slip(Long id, String advice) {
		this.id = id;
		this.advice = advice;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Long getId() {
		return id;
	}	
	

}
