package by.arvisit.modsenlibapp.exceptionhandlingstarter.exception;

import java.time.ZonedDateTime;
import java.util.Map;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record MultiExceptionResponse(int status, Map<String, String> messages, ZonedDateTime timeStamp) {

}
