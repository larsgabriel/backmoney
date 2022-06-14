package com.moneyestudoapi.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerApi extends ResponseEntityExceptionHandler {
	//aqui um exemplo de implementação de classe para gerar exception, neste caso sera
	//gerada uma exceção quando houver um erro de mensagem não lida, por exemplo, se vc
	//tentar salvar um objeto desconhecido. A anotação controlleradvice é responsavel por ouvir toda aplicação
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return super.handleHttpMessageNotReadable(ex, headers, HttpStatus.BAD_REQUEST, request);
	}
}
