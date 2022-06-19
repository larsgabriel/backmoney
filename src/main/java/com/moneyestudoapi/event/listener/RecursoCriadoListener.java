package com.moneyestudoapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.moneyestudoapi.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {
	
	
	//Este sera o listener, ele vai ficar ouvindo o evento referenciado no caso aqui é o recursocriadoevent 
	//Dentro dos controllers onde é executado o evento de criar um recurso existe uma chamada para classe ApplicationEventPublisher
	//onde é passado 3 paramentros, vide a classe de controller
	
	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoevent) {
		
		HttpServletResponse response = recursoCriadoevent.getResponse();
		Long codigo = recursoCriadoevent.getCodigo();
		
		adicionarHeaderLocation(response, codigo);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		// a partir do retorno da requisição eu vou retornar o codigo da categoria salva no location do header
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri(); 
		response.setHeader("Location", uri.toASCIIString());
	}

}
