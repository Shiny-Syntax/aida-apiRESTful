package com.shinysyntax.aida.aida.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // -------------------------------------------------------------
    // 422 — VALIDAÇÃO
    // -------------------------------------------------------------
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull org.springframework.http.HttpStatusCode status,
            @NonNull WebRequest request) {

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> validationErrors.put(err.getField(), err.getDefaultMessage()));

        ApiResponse body = ApiResponse.error(
            400,
            "Validation Error",
            "Validation error.",
                extractPath(request),
                validationErrors
        );

        return ResponseEntity.badRequest().body(body);
    }

        // -------------------------------------------------------------
        // 400 — Malformed JSON / unreadable message
        // -------------------------------------------------------------
        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull org.springframework.http.HttpStatusCode status,
            @NonNull WebRequest request) {

        Throwable cause = ex.getMostSpecificCause();
        String detail = (cause == null || cause.getMessage() == null) ? ex.getMessage() : cause.getMessage();
        ApiResponse body = ApiResponse.error(
            400,
            "Bad Request",
            "Request body is invalid or malformed.",
            extractPath(request),
            Map.of("details", detail)
        );

        return ResponseEntity.badRequest().body(body);
        }

        // -------------------------------------------------------------
        // 400 — Constraint violations (e.g., @Validated on method params)
        // -------------------------------------------------------------
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(v -> ((ConstraintViolation<?>) v).getPropertyPath().toString(), v -> ((ConstraintViolation<?>) v).getMessage(), (a, b) -> a));

        ApiResponse body = ApiResponse.error(
            400,
            "Bad Request",
            "Invalid parameters.",
            extractPath(request),
            errors
        );

        return ResponseEntity.badRequest().body(body);
        }

        // -------------------------------------------------------------
        // 400 — Type mismatch (e.g., path variable / request param wrong type)
        // -------------------------------------------------------------
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        Class<?> reqType = ex.getRequiredType();
        String expectedType = reqType == null ? "unknown" : reqType.getSimpleName();
        Map<String, String> details = Map.of(
            ex.getName(), "Invalid value: expected type " + expectedType
        );

        ApiResponse body = ApiResponse.error(
            400,
            "Bad Request",
            "Parameter has invalid type.",
            extractPath(request),
            details
        );

        return ResponseEntity.badRequest().body(body);
        }

    // -------------------------------------------------------------
    // 404 — COLABORADOR NOT FOUND (custom)
    // -------------------------------------------------------------
    @ExceptionHandler(ColaboradorNotFoundException.class)
    public ResponseEntity<Object> handleColaboradorNotFound(ColaboradorNotFoundException ex, WebRequest request) {

        ApiResponse body = ApiResponse.error(
                404,
                "Collaborator Not Found",
                ex.getMessage(),
                extractPath(request),
                null
            );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // -------------------------------------------------------------
    // 404 — NOT FOUND (generic)
    // -------------------------------------------------------------
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {

        ApiResponse body = ApiResponse.error(
                404,
                "Not Found",
                ex.getMessage(),
                extractPath(request),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // -------------------------------------------------------------
    // 400 — BAD REQUEST
    // -------------------------------------------------------------
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {

        ApiResponse body = ApiResponse.error(
                400,
                "Bad Request",
                ex.getMessage(),
                extractPath(request),
                null
        );

        return ResponseEntity.badRequest().body(body);
    }

    // -------------------------------------------------------------
    // 409 — CONFLITO (duplicação, violação de constraint)
    // -------------------------------------------------------------
    @ExceptionHandler(com.shinysyntax.aida.aida.exception.ConflictException.class)
    public ResponseEntity<Object> handleConflictException(com.shinysyntax.aida.aida.exception.ConflictException ex, WebRequest request) {

        ApiResponse body = ApiResponse.error(
                409,
                "Conflict",
                ex.getMessage(),
                extractPath(request),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConflict(DataIntegrityViolationException ex, WebRequest request) {

        ApiResponse body = ApiResponse.error(
            409,
            "Conflict",
            "Data conflict.",
            extractPath(request),
            Map.of("details", ex.getMostSpecificCause().getMessage())
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // -------------------------------------------------------------
    // 302 — REDIRECT
    // -------------------------------------------------------------
    @ExceptionHandler(RedirectException.class)
    public ResponseEntity<Object> handleRedirect(RedirectException ex, WebRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(java.net.URI.create(ex.getLocation()));

        ApiResponse body = ApiResponse.success(
                302,
                ex.getMessage(),
                extractPath(request)
        );

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).body(body);
    }

    // -------------------------------------------------------------
    // 503 — SERVICE UNAVAILABLE
    // -------------------------------------------------------------
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handleServiceUnavailable(ServiceUnavailableException ex, WebRequest request) {

        ApiResponse body = ApiResponse.error(
            503,
            "Service Unavailable",
            "Service temporarily unavailable.",
            extractPath(request),
            Map.of("details", ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    // -------------------------------------------------------------
    // 500 — INTERNAL SERVER ERROR
    // -------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOther(Exception ex, WebRequest request) {

        ApiResponse body = ApiResponse.error(
            500,
            "Internal Server Error",
            "Internal server error.",
            extractPath(request),
            Map.of("details", ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // -------------------------------------------------------------
    // UTILITÁRIO → extrair /api/xxxx do WebRequest
    // -------------------------------------------------------------
    private String extractPath(WebRequest request) {
        String desc = request.getDescription(false); // "uri=/api/..."
        return desc.replace("uri=", "");
    }
}
