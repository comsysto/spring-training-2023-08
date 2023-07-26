package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.common.BearerToken;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.common.RequestId;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HeaderFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final ContextManager contextManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestId = Optional.ofNullable(request.getHeader(RequestId.HEADER_NAME))
                .map(RequestId::new)
                .orElseGet(RequestId::random);

        var token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(s -> s.startsWith(BEARER_PREFIX))
                .map(s -> s.substring(BEARER_PREFIX.length()))
                .map(BearerToken::new);

        var context = new Context( requestId, token);
        try{
            contextManager.setContext(context);
            filterChain.doFilter(request, response);
        }finally {
            contextManager.reset();
        }
    }
}
