package team.backend.goWithMe.global.error.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, CommonErrorCode.ENTITY_NOT_FOUND);
    }
}
