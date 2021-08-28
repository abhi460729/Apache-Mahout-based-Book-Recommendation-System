package bookmartapplication.main.entityLayer.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookmartapplication.main.dao.OrderItemRepository;
import bookmartapplication.main.entities.OrderItem;
import bookmartapplication.main.entityLayer.repo.OrderItemEntityLayerService;
import bookmartapplication.main.exceptions.OrderItemNotFoundException;

@Service
public class OrdrItemEntityLayerServiceImpl implements OrderItemEntityLayerService{
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public OrderItem createOrderItem(OrderItem order) {
		OrderItem savedOrder=orderItemRepository.save(order);
		return savedOrder;
	}
	
    public List<OrderItem> readAllOrderItem() {
        return orderItemRepository.findAll();
    }
	
	public Optional<OrderItem> readOrderItem(int id) {
		Optional<OrderItem> order = orderItemRepository.findById(id);
		if(!order.isPresent()) {
			throw new OrderItemNotFoundException("id-"+id);
		}
		return order;
	}
	
	public void deleteOrderItem(int id) {
		orderItemRepository.deleteById(id);
		return;
	}
	
	public void deleteAllOrderItem() {
		orderItemRepository.deleteAll();
		return;
	}
}
