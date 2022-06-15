package com.moneyestudoapi.exceptionhandler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Data;

@ControllerAdvice
public class ExceptionHandlerApi extends ResponseEntityExceptionHandler {
	
	//Este é responsavel por ler o messages.properties
	@Autowired
	private MessageSource messageSource;
	
	//aqui um exemplo de implementação de classe para gerar exception, neste caso sera
	//gerada uma exceção quando houver um erro de mensagem não lida, por exemplo, se vc
	//tentar salvar um objeto desconhecido. A anotação controlleradvice é responsavel por ouvir toda aplicação
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			Erro erro, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String msgUser = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String msgDev = ex.getCause().toString();
		
		return handleHttpMessageNotReadable(ex, new Erro(msgUser, msgDev),headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		java.util.List<Erro> erros = criarListaErros(ex.getBindingResult());
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private java.util.List<Erro> criarListaErros(BindingResult bindingResult){
		
		java.util.List<Erro> erros = new ArrayList<>();
				
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemDev = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemUser = fieldError.toString();
			
			erros.add(new Erro(mensagemUser, mensagemDev));
			
		}
		return erros;
		
	}
	
	@Data
	public class Erro {
	
	private String mensagemUsuario;
	private String mensagemDesenvolvedor;
	
	public Erro (String mensagemUsuario, String mensagemDesenvolvedor) {
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		this.mensagemUsuario = mensagemUsuario;
	}
	}
}
