package bookmartapplication.main.entityLayer.repo;


import java.util.List;
import java.util.Optional;
import bookmartapplication.main.entities.Orders;

public interface OrderEntityLayerService {
	public Orders createOrder(Orders order);
	public List<Orders> readAllOrder();
	public Optional<Orders> readOrder(int id);
	public void deleteOrder(int id);
	public void deleteAllOrder();
}
