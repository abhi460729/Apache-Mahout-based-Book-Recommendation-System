package bookmartapplication.main.entityLayer.repo;

import java.util.List;
import java.util.Optional;

import bookmartapplication.main.entities.User;

public interface UserEntityLayerService {
	
	public User createUser(User user);
	public List<User> readAllUsers();
	public Optional<User> readUser(int id);
	public void deleteUser(int id) ;
	public void deleteAllUsers();
	
}
