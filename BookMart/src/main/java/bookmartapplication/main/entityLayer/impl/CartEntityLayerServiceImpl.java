package bookmartapplication.main.entityLayer.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookmartapplication.main.dao.CartRepository;
import bookmartapplication.main.entities.Cart;
import bookmartapplication.main.entityLayer.repo.CartEntityLayerService;
import bookmartapplication.main.exceptions.CartNotFoundException;
@Service
public class CartEntityLayerServiceImpl implements CartEntityLayerService{
	@Autowired
	private CartRepository cartRepository;
	
	
	public Cart createCart(Cart cart) {
		Cart savedCart=cartRepository.save(cart);
		return savedCart;
	}
	
	
    public List<Cart> readAllCart() {
        return cartRepository.findAll();
    }
	
	
	public Optional<Cart> readCart(int id) {
		Optional<Cart> cart = cartRepository.findById(id);
		if(!cart.isPresent()) {
			throw new CartNotFoundException("id-"+id);
		}
		return cart;
	}
	
	
	public void deleteCart(int id) {
		cartRepository.deleteById(id);
		return;
	}
	
	public void deleteAllCarts() {
		cartRepository.deleteAll();
		return;
	}
}



