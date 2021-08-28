package bookmartapplication.main.entityLayer.repo;

import java.util.List;
import java.util.Optional;
import bookmartapplication.main.entities.Rating;

public interface RatingEntityLayerService {
	public Rating createRating(Rating rating);
    public List<Rating> readAllRatings();
	public Optional<Rating> readRating(int id);
	public void deleteRating(int id);
	public void deleteAllRatings();
}
