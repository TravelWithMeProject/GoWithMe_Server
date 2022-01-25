package team.backend.goWithMe.domain.trip.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.backend.goWithMe.domain.trip.error.exception.*;
import team.backend.goWithMe.global.error.ErrorResponse;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice(basePackages = "team.backend.goWithMe.domain")
public class TripErrorController {

    @ExceptionHandler(TimeTableNameInvalidException.class)
    public ResponseEntity<ErrorResponse> wrongTimeTableOwnerIdHandler(TimeTableNameInvalidException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, header(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(WrongTimeTableOwnerIdException.class)
    public ResponseEntity<ErrorResponse> wrongTimeTableOwnerIdHandler(WrongTimeTableOwnerIdException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, header(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeTablePeriodInvalidException.class)
    public ResponseEntity<ErrorResponse> timeTablePeriodInvalidHandler(TimeTablePeriodInvalidException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, header(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchTimeTableException.class)
    public ResponseEntity<ErrorResponse> noSuchTimeTableHandler(NoSuchTimeTableException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, header(), HttpStatus.NOT_FOUND);
    }

    private HttpHeaders header() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return httpHeaders;
    }

    /**
     * Member 관련 예외 핸들러임. 추후 반드시 삭제해야함
     */
    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity<ErrorResponse> noMemberHandler(NoSuchMemberException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, header(), HttpStatus.NOT_FOUND);
    }
}