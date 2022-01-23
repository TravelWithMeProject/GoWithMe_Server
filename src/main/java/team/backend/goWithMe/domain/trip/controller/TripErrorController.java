package team.backend.goWithMe.domain.trip.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableErrorResponseDTO;
import team.backend.goWithMe.domain.trip.error.exception.*;
import team.backend.goWithMe.global.error.ErrorResponse;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice(basePackages = "team.backend.goWithMe.domain.trip")
public class TripErrorController {

    @ExceptionHandler(TimeTableNameInvalidException.class)
    public ResponseEntity<TimeTableErrorResponseDTO> wrongTimeTableOwnerIdHandler(TimeTableNameInvalidException e) {
        TimeTableErrorResponseDTO dto = new TimeTableErrorResponseDTO(e.getMessage(), e.getErrorCode());

        return new ResponseEntity<>(dto, header(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(WrongTimeTableOwnerIdException.class)
    public ResponseEntity<TimeTableErrorResponseDTO> wrongTimeTableOwnerIdHandler(WrongTimeTableOwnerIdException e) {
        TimeTableErrorResponseDTO dto = new TimeTableErrorResponseDTO(e.getMessage(), e.getErrorCode());

        return new ResponseEntity<>(dto, header(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeTablePeriodInvalidException.class)
    public ResponseEntity<TimeTableErrorResponseDTO> timeTablePeriodInvalidHandler(TimeTablePeriodInvalidException e) {
        TimeTableErrorResponseDTO dto = new TimeTableErrorResponseDTO(e.getMessage(), e.getErrorCode());

        return new ResponseEntity<>(dto, header(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchTimeTableException.class)
    public ResponseEntity<TimeTableErrorResponseDTO> noSuchTimeTableHandler(NoSuchTimeTableException e) {
        TimeTableErrorResponseDTO dto = new TimeTableErrorResponseDTO(e.getMessage(), e.getErrorCode());

        return new ResponseEntity<>(dto, header(), HttpStatus.NOT_FOUND);
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
        ErrorResponse dto = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(dto, header(), HttpStatus.NOT_FOUND);
    }
}