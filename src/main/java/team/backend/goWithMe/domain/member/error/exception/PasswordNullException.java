package team.backend.goWithMe.domain.member.error.exception;

public class PasswordNullException extends UserBusinessException {

    public PasswordNullException(String message) {
        super(message);
    }
}
