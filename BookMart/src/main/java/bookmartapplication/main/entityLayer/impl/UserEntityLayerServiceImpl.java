package bookmartapplication.main.entityLayer.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bookmartapplication.main.dao.UserRepository;
import bookmartapplication.main.entities.User;
import bookmartapplication.main.entityLayer.repo.UserEntityLayerService;
import bookmartapplication.main.exceptions.UserNotFoundException;

@Service
public class UserEntityLayerServiceImpl implements UserEntityLayerService{
	
	@Autowired
	private UserRepository userRepository;
	
	public User createUser(User user) {
		User savedUser=userRepository.save(user);
		return savedUser;
	}
	
    public List<User> readAllUsers() {
        return userRepository.findAll();
    }
    
	public Optional<User> readUser(int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		return user;
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
		return;
	}
	 
	public void deleteAllUsers() {
		userRepository.deleteAll();
		return;
	}
	
}
