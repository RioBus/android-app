package com.tormentaLabs.riobus.exception;

public class CustomException extends Exception {

	/** variavel de serial. */
	private static final long serialVersionUID = 1L;

	private String msg;

	public CustomException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public CustomException() {
		String erro ="Ocorreu um erro inesperado, contate o suporte."; 
		this.msg = erro;
	}

	public String getMessage() {
		return msg;
	}

}
