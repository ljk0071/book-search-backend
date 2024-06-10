package com.booksearch.security;

import com.booksearch.dto.util.ApiResponse;
import com.booksearch.exception.NotFoundUserException;
import com.booksearch.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
//public class TestFilter extends OncePerRequestFilter {
public class TestFilter implements Filter {

    private final ObjectMapper objectMapper;

    private final AuthenticationProviderService service;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest wrappedRequest = new RequestWrapper((HttpServletRequest) request);
        try (InputStream is = wrappedRequest.getInputStream()) {
            User user = objectMapper.readValue(is, User.class);
            Authentication authUser = service.authenticate(UsernamePasswordAuthenticationToken
                    .unauthenticated(user.getUserId(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authUser);
            chain.doFilter(wrappedRequest, response);
        } catch (NotFoundUserException e) {
            ((HttpServletResponse) response).setStatus(HttpStatus.BAD_REQUEST.value());
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json;charset=utf-8");
            response.getWriter().println(objectMapper.writeValueAsString(ApiResponse.fail(e.getMessage())));
        }
    }

//    @Override
//    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        ServletRequest wrappedRequest = new RequestWrapper(request);
//        try (InputStream is = wrappedRequest.getInputStream()) {
//            User user = objectMapper.readValue(is, User.class);
//            Authentication authUser = service.authenticate(UsernamePasswordAuthenticationToken
//                    .unauthenticated(user.getUserId(),
//                            user.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authUser);
//            chain.doFilter(wrappedRequest, response);
//        } catch (NotFoundUserException e) {
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.setHeader("Content-Type", "application/json;charset=utf-8");
//            response.getWriter().println(objectMapper.writeValueAsString(ApiResponse.fail(e.getMessage())));
//        }
//    }
}
