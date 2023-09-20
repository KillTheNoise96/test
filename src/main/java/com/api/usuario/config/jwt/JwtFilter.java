package com.api.usuario.config.jwt;

import com.api.usuario.config.exception.ExceptionResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtFilter extends GenericFilterBean {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter()
                        .write(convertObjectToJson(createMessage("Agregar token.")));
                return;
            }
        }
        final String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(convertObjectToJson(createMessage("Token no válido.")));
            return;
        }

        filterChain.doFilter(request, response);
    }

    @JsonIgnore
    private static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    private Map<String, String> createMessage(String message) {
        Map<String, String> txt = new HashMap<>();
        txt.put("message", message);
        return txt;
    }
}
