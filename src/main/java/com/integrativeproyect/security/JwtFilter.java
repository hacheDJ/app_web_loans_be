package com.integrativeproyect.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtFilter extends OncePerRequestFilter{
	
	private List<String> lstWhiteUris = List.of("/auth", "/test", "/group", "/listUserByRoleForUpdateLender");
	private JwtHandler jwtHandler;
	
	public JwtFilter(JwtHandler jwtHandler) {
		this.jwtHandler = jwtHandler;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		System.out.println("-----> DENTRO DEL SHOULDNOTFILTER "+request.getRequestURI());
		//System.out.println("-----> BOOLEAN "+lstWhiteUris.stream().anyMatch(url -> request.getRequestURI().contains(url)));
		return lstWhiteUris.stream().anyMatch(url -> request.getRequestURI().contains(url));
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain) throws ServletException, IOException {
		
		/*if(this.shouldNotFilter(request)) { 
			System.out.println("----> IF >>>>>>>> " + this.shouldNotFilter(request));
			filterChain.doFilter(request, response);
			return;
		}*/
		
		System.out.println("-----> DENTRO DEL FILTRO");
		System.out.println("-----> TOKEN ----->"+request.getHeader("Authorization"));

		final String authHeader = request.getHeader("Authorization");
		
		//System.out.println("-----> "+split.length);
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			//response.setStatus(response.SC_UNAUTHORIZED);
			System.out.println("-----> NO SE ENCONTRO BEARER");
			filterChain.doFilter(request, response);
			return;
		}
		
		final String[] split = authHeader.split(" ");
		final String jwt;
		System.out.println("-----> SI SE ENCONTRO BEARER");
		//System.out.println("-----> "+jwtHandler);
		
		if(split.length < 2) {
			System.out.println("-----> PERO NO HAY TOKEN");
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = split[1];
		Authentication authentication = jwtHandler.verifyToken(jwt);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("-----> "+ SecurityContextHolder.getContext());

		filterChain.doFilter(request, response);
	}

}
