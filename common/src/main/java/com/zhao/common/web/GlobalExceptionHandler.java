package com.zhao.common.web;

import com.zhao.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>fail(e.getErrorCode(), e.getMessage())
                .path(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 处理参数校验异常 (Bean Validation)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        log.warn("参数校验失败: {}", message);
        ApiResponse<Void> response = ApiResponse.<Void>fail(ErrorCode.VALIDATION_ERROR, message)
                .path(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        log.warn("参数绑定失败: {}", message);
        ApiResponse<Void> response = ApiResponse.<Void>fail(ErrorCode.VALIDATION_ERROR, message)
                .path(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 处理约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(
            ConstraintViolationException e, HttpServletRequest request) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        
        log.warn("约束违反: {}", message);
        ApiResponse<Void> response = ApiResponse.<Void>fail(ErrorCode.VALIDATION_ERROR, message)
                .path(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String message = String.format("参数 '%s' 类型不匹配，期望类型: %s", 
                e.getName(), e.getRequiredType().getSimpleName());
        
        log.warn("参数类型不匹配: {}", message);
        ApiResponse<Void> response = ApiResponse.<Void>fail(ErrorCode.PARAMETER_ERROR, message)
                .path(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 处理404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFoundException(
            NoHandlerFoundException e, HttpServletRequest request) {
        String message = String.format("请求路径 '%s' 不存在", e.getRequestURL());
        
        log.warn("请求路径不存在: {}", message);
        ApiResponse<Void> response = ApiResponse.<Void>fail(ErrorCode.RESOURCE_NOT_FOUND, message)
                .path(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 处理IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException e, HttpServletRequest request) {
        log.warn("非法参数: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>fail(ErrorCode.PARAMETER_ERROR, e.getMessage())
                .path(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 处理IllegalStateException
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalStateException(
            IllegalStateException e, HttpServletRequest request) {
        log.warn("非法状态: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>fail(ErrorCode.SYSTEM_ERROR, e.getMessage())
                .path(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {}", e.getMessage(), e);
        ApiResponse<Void> response = ApiResponse.<Void>systemError("系统内部错误，请联系管理员")
                .path(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(
            RuntimeException e, HttpServletRequest request) {
        log.error("运行时异常: {}", e.getMessage(), e);
        ApiResponse<Void> response = ApiResponse.<Void>systemError("系统运行异常，请联系管理员")
                .path(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


