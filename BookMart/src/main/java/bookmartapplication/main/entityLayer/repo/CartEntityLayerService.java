package bookmartapplication.main.entityLayer.repo;

import java.util.List;
import java.util.Optional;

import bookmartapplication.main.entities.Cart;

public interface CartEntityLayerService {
	public Cart createCart(Cart cart);
    public List<Cart> readAllCart();
	public Optional<Cart> readCart(int id);
	public void deleteCart(int id);
	public void deleteAllCarts();
}
