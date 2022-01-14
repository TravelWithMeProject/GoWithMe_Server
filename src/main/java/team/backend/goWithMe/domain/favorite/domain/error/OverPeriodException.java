package team.backend.goWithMe.domain.favorite.domain.error;

public class OverPeriodException extends FavoriteBusinessException {

    public OverPeriodException(String message) {
        super(message);
    }
}
