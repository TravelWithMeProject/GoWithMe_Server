package team.backend.goWithMe.domain.member.error.exception;

public class PasswordMissMatchException extends UserBusinessException {

    public PasswordMissMatchException(String message) {
        super(message);
    }
}
