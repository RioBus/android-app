package com.tormentaLabs.riobus.exception;

public class RioBusException extends Exception {

	/** variavel de serial. */
	private static final long serialVersionUID = 1L;

	private String msg;

	public RioBusException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public RioBusException() {
		String erro ="Ocorreu um erro inesperado, contate o suporte."; 
		this.msg = erro;
	}

	public String getMessage() {
		return msg;
	}

}
