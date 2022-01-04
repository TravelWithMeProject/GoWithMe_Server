package team.backend.goWithMe.global.error.exception;

public interface ErrorCode {

    int status();
    String message();
    String code();
}
