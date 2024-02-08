package by.arvisit.modsenlibapp.exceptionhandlingstarter.handler;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.arvisit.modsenlibapp.exceptionhandlingstarter.exception.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityExceptionHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    private static final ZoneId EUROPE_MINSK_TIMEZONE = ZoneId.of("Europe/Minsk");

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        sendExceptionResponse(response, ExceptionResponse.builder()
                .withStatus(HttpStatus.UNAUTHORIZED.value())
                .withMessage(authException.getMessage())
                .withTimeStamp(ZonedDateTime.now(EUROPE_MINSK_TIMEZONE))
                .build());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        sendExceptionResponse(response, ExceptionResponse.builder()
                .withStatus(HttpStatus.FORBIDDEN.value())
                .withMessage(accessDeniedException.getMessage())
                .withTimeStamp(ZonedDateTime.now(EUROPE_MINSK_TIMEZONE))
                .build());
    }

    private void sendExceptionResponse(HttpServletResponse response, ExceptionResponse message) throws IOException {
        response.setStatus(message.status());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(objectMapper.writeValueAsString(message));
    }

}
