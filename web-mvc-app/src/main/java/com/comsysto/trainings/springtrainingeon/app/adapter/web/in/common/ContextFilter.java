package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class ContextFilter extends OncePerRequestFilter
{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException
	{
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		log.info("auth token: {}", authHeader);
		response.setContentType("text/plain");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().println("Booom");
		response.getWriter().flush();
//		filterChain.doFilter(request, response);
	}
}
