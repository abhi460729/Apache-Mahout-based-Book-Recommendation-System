package bookmartapplication.main.entityLayer.impl;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bookmartapplication.main.dao.RatingRepository;
import bookmartapplication.main.entities.Rating;
import bookmartapplication.main.entityLayer.repo.RatingEntityLayerService;
import bookmartapplication.main.exceptions.RatingNotFoundException;
@Service
public class RatingEntityLayerServiceImpl implements RatingEntityLayerService{
	@Autowired
	private RatingRepository ratingRepository;

	public Rating createRating(Rating rating) {
		Rating save = ratingRepository.save(rating);
		return save;
	}
	
    public List<Rating> readAllRatings() {
        return ratingRepository.findAll();
    }
	
	public Optional<Rating> readRating(int id) {
		Optional<Rating> rating = ratingRepository.findById(id);
		if(!rating.isPresent()) {
			throw new RatingNotFoundException("id"+id);
		}
		return rating;
	}
	
	public void deleteRating(int id) {
		ratingRepository.deleteById(id);
		return;
	}
	
	public void deleteAllRatings() {
		ratingRepository.deleteAll();
		return;
	}
}
