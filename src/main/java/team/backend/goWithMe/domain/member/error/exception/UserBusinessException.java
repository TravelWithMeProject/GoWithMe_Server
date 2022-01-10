package team.backend.goWithMe.domain.member.error.exception;

public class UserBusinessException extends RuntimeException {

    public UserBusinessException(String message) {
        super(message);
    }
}
