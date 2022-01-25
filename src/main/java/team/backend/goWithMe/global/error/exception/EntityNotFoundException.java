package team.backend.goWithMe.global.error.exception;

// Not Found Exception Root
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
