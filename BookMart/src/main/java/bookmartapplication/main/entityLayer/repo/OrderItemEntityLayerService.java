package bookmartapplication.main.entityLayer.repo;

import java.util.List;
import java.util.Optional;

import bookmartapplication.main.entities.OrderItem;

public interface OrderItemEntityLayerService {
	public OrderItem createOrderItem(OrderItem order);
    public List<OrderItem> readAllOrderItem();
	public Optional<OrderItem> readOrderItem(int id);
	public void deleteOrderItem(int id);
	public void deleteAllOrderItem();
}
