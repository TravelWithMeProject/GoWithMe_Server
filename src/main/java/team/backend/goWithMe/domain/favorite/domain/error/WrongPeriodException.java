package team.backend.goWithMe.domain.favorite.domain.error;

public class WrongPeriodException extends FavoriteBusinessException {

    public WrongPeriodException(String message) {
        super(message);
    }
}
