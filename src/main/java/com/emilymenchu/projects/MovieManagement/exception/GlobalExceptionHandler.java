package com.emilymenchu.projects.MovieManagement.exception;

import com.emilymenchu.projects.MovieManagement.dto.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            Exception.class,
            ObjectNotFoundException.class,
            InvalidPasswordException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            DuplicateRatingException.class
    })
    public ResponseEntity<ApiError> handleGenericException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        ZoneId zoneId = ZoneId.of("America/Guatemala");
        LocalDateTime timestamp = LocalDateTime.now(zoneId);

        return switch (exception) {
            case ObjectNotFoundException objectNotFoundException ->
                    this.handleObjectNotfoundException(objectNotFoundException, request, response, timestamp);
            case InvalidPasswordException invalidPasswordException ->
                    this.handleInvalidPasswordException(invalidPasswordException, request, response, timestamp);
            case MethodArgumentTypeMismatchException methodArgumentTypeMismatchException ->
                    this.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, request, response, timestamp);
            case MethodArgumentNotValidException methodArgumentNotValidException ->
                    this.handleMethodArgumentNotValidException(methodArgumentNotValidException, request, response, timestamp);
            case HttpRequestMethodNotSupportedException methodNotSupportedException ->
                    this.handleHttpRequestMethodNotSupportedException(methodNotSupportedException, request, response, timestamp);
            case HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException ->
                    this.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, request, response, timestamp);
            case HttpMessageNotReadableException httpMessageNotReadableException ->
                    this.handleHttpMessageNotReadableException(httpMessageNotReadableException, request, response, timestamp);
            case DuplicateRatingException duplicateRatingException ->
                    this.handleDuplicateRatingException(duplicateRatingException, request, response, timestamp);
            case null, default -> this.handleException(exception, request, response, timestamp);
        };
    }

    private ResponseEntity<ApiError> handleObjectNotfoundException(ObjectNotFoundException objectNotFoundException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.NOT_FOUND.value();
        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "We are sorry, the requested information could not be found. Please check the URL or try another search.",
                objectNotFoundException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();
        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid Password: The provided password does not meet the required criteria, " + invalidPasswordException.getErrorDescription(),
                invalidPasswordException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oosp! Something went wrong on our server. Please try again later.",
                exception.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();
        Object valueRejected = methodArgumentTypeMismatchException.getValue();
        String propertyName = methodArgumentTypeMismatchException.getName();
        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid Request: The provided value '"+valueRejected+"' does not have the expected data type for the "+propertyName+".",
                methodArgumentTypeMismatchException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        List<ObjectError> errors = methodArgumentNotValidException.getAllErrors();
        List<String> details = errors.stream().map(e -> {
            if (e instanceof FieldError fieldError) {
                return fieldError.getField() + ": " + fieldError.getDefaultMessage();
            }
            return  e.getDefaultMessage();
        }).toList();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The request contains invalid or incomplete parameters. Please verify and provide the required information before trying again.",
                methodArgumentNotValidException.getMessage(),
                timestamp,
                details
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException methodNotSupportedException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops! Method Not Allowed. Check the HTTP method of your request.",
                methodNotSupportedException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Unsupported Media Type: The server is unable to process the requested entity in the format provided in the request." +
                        "Supported media types are: " + httpMediaTypeNotSupportedException.getSupportedMediaTypes() + " and you sent: " +
                        httpMediaTypeNotSupportedException.getContentType(),
                httpMediaTypeNotSupportedException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops! Error reading the HTTP message body. Make sure the request is correctly formatted and contains valid data.",
                httpMessageNotReadableException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleDuplicateRatingException(DuplicateRatingException duplicateRatingException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamp) {
        int httpStatus = HttpStatus.CONFLICT.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                duplicateRatingException.getMessage(),
                duplicateRatingException.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}