package bookmartapplication.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmartapplication.main.entities.Rating;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{

}
