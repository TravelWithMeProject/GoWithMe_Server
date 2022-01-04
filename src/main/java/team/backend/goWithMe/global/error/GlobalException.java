package team.backend.goWithMe.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.CommonErrorCode;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<CommonErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);

        final CommonErrorResponse response = CommonErrorResponse.of(CommonErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<CommonErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);

        final CommonErrorResponse response = CommonErrorResponse.of(CommonErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(CommonErrorCode.HANDLE_ACCESS_DENIED.status()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<CommonErrorResponse> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException", e);

        final CommonErrorCode commonErrorCode = e.getErrorCode();
        final CommonErrorResponse response = CommonErrorResponse.of(commonErrorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(commonErrorCode.status()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonErrorResponse> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        final CommonErrorResponse response = CommonErrorResponse.of(CommonErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
