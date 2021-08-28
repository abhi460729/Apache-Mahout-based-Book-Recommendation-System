package bookmartapplication.main.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import bookmartapplication.main.dao.UserRepository;
import bookmartapplication.main.entities.User;
import bookmartapplication.main.exceptions.UserNotFoundException;
import bookmartapplication.main.utilities.Constants;


@Controller
@RequestMapping(path="/user")
public class UserEntityController {		
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/create")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser=userRepository.save(user);
		
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/readAll")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
    public @ResponseBody List<User> readAllUsers() {
        return userRepository.findAll();
    }
	
	@GetMapping(path = "/read/{id}")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE,key="'UserInCache'+#id")
	public @ResponseBody Optional<User> readUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		return user;
	}
	
	@DeleteMapping("delete/{id}")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,key="'UserInCache'+#id") 
	public @ResponseBody void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
		return;
	}
	
	@DeleteMapping("/delete")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,allEntries = true) 
	public @ResponseBody void deleteAllUsers() {
		userRepository.deleteAll();
		return;
	}
	
}
