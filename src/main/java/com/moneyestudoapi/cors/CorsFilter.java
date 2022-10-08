package com.moneyestudoapi.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.moneyestudoapi.config.properties.BackProperties;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	
	
	@Autowired
	private BackProperties properties;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//aqui são permissões do token que sempre devem ser permitidas para o envio.
		response.setHeader("Access-Control-Allow-Origin", properties.getOriginPermitida());
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		
		//aqui ele verifica se é uma requisição options e adiciona permissões para passar do CORS.
		if("OPTIONS".equals(request.getMethod()) && properties.getOriginPermitida().equals(request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			response.setHeader("Access-Control-Allow-Methods", "Authorization, Content-Type, Accept");
			response.setHeader("Access-Control-Max-Age", "3600");
			
			
		}else {
			
			chain.doFilter(req, resp);
		}
		
	}

}
