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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import bookmartapplication.main.entities.Cart;
import bookmartapplication.main.entityLayer.repo.CartEntityLayerService;
import bookmartapplication.main.exceptions.CartNotFoundException;
import bookmartapplication.main.utilities.Constants;

@Controller
@RequestMapping(path="/cart")
public class CartEntityController {
	
	@Autowired
	private CartEntityLayerService cartEntityLayerService;
	
	@GetMapping("/create")
	public ResponseEntity<Object> createCart(@RequestBody Cart cart) {
		Cart savedCart=cartEntityLayerService.createCart(cart);
		
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedCart.getCart_id())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/readAll")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
    public @ResponseBody List<Cart> readAllCart() {
        return cartEntityLayerService.readAllCart();
    }
	
	@GetMapping(path = "/read/{id}")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE,key="'CartInCache'+#id")
	public @ResponseBody Optional<Cart> readCart(@PathVariable int id) {
		Optional<Cart> cart = cartEntityLayerService.readCart(id);
		if(!cart.isPresent()) {
			throw new CartNotFoundException("id-"+id);
		}
		return cart;
	}
	
	@DeleteMapping("delete/{id}")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,key="'CartInCache'+#id")
	public @ResponseBody void deleteCart(@PathVariable int id) {
		cartEntityLayerService.deleteCart(id);
		return;
	}
	
	@DeleteMapping("/delete")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,allEntries = true)
	public @ResponseBody void deleteAllCarts() {
		cartEntityLayerService.deleteAllCarts();
		return;
	}
}
