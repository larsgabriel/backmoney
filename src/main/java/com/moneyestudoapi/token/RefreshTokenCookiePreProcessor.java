package com.moneyestudoapi.token;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//Esta classe é responsavel por colocar o refresh token em todas as requisições automaticamente

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // esta anotação deixa este filtro com prioridade alta antes de todo mundo.
public class RefreshTokenCookiePreProcessor implements Filter {

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) request;

                if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
                    && "refresh_token".equals(req.getParameter("grant_type"))
                    && req.getCookies() != null) {
              
                	
//                	if ("/oauth/token".equals(req.getRequestURI())
//            				&& "refresh_token".equals(req.getParameter("grant_type"))
//            				&& req.getCookies() != null) {
//
//            			for (Cookie cookie : req.getCookies()) {
//            				if (cookie.getName().equals("refresh_token")) {
//            					String refreshToken = cookie.getValue();
//            					req = new MyServletRequestWrapper(req, refreshToken);
//            				}
//
//            			} 	Abaixo transformando o for em stream
                	
                	
                  String refreshToken = 
                      Stream.of(req.getCookies())
                          .filter(cookie -> "refreshToken".equals(cookie.getName()))
                          .findFirst()
                          .map(cookie -> cookie.getValue())
                          .orElse(null);
              
                  req = new MyServletRequestWrapper(req, refreshToken);
                }
              
                chain.doFilter(req, response);
        
    }
    
    static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;
		
		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken });
			map.setLocked(true);
			return map;
		}
		
	}
}