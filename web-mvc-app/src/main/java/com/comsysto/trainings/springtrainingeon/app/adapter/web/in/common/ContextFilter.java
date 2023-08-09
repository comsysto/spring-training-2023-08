package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import com.comsysto.trainings.springtrainingeon.app.port.context.out.BearerToken;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextRepository;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.RequestId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContextFilter extends OncePerRequestFilter {
    private final ContextRepository contextRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestId = request.getHeader("requestId");

        Context context = new Context(new RequestId(requestId), new BearerToken(authHeader));
        contextRepository.setContext(context);

        log.info("auth token: {}", authHeader);
        try {
            filterChain.doFilter(request, response);
        } finally {
            contextRepository.reset();
        }
    }
}
