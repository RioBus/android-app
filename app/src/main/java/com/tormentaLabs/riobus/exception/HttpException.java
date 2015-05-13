package com.tormentaLabs.riobus.exception;

public class HttpException extends CustomException {

	public HttpException(String mensagem) {
		super(mensagem);
	}

	public HttpException(int code) {
		super(handleLoginException(code));
	}
	
	private static String handleLoginException(int code) {
		switch (code) {
			case 401:
				return "Usuário e/ou senha inválidos";
			case 403:
				return "Usuário não tem autorização para realizar a ação.";
			case 408:
				return "Tempo limite para operação foi excedido.";
			case 500:
				return "Ocorreu um erro no servidor. Tente novamente mais tarde.";
			case 502:
				return "Dados enviados são inválidos.";
			case 503:
				return "Servidor fora do ar, tente novamente mais tarde.";
			default:
				break;
		}
		return "Ocorreu um erro inesperado, contate o suporte.";
	}

}
