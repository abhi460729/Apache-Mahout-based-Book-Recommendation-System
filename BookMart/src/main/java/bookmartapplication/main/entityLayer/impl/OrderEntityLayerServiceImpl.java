package bookmartapplication.main.entityLayer.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bookmartapplication.main.dao.OrderRepository;
import bookmartapplication.main.entities.Orders;
import bookmartapplication.main.entityLayer.repo.OrderEntityLayerService;
import bookmartapplication.main.exceptions.OrderItemNotFoundException;
@Service
public class OrderEntityLayerServiceImpl implements OrderEntityLayerService{

	@Autowired
	private OrderRepository orderRepository;
	
	public Orders createOrder(Orders order) {
		Orders savedOrder=orderRepository.save(order);
		return savedOrder;
	}
	
	public List<Orders> readAllOrder() {
        return orderRepository.findAll();
    }
	
	public Optional<Orders> readOrder(int id) {
		Optional<Orders> order = orderRepository.findById(id);
		if(!order.isPresent()) {
			throw new OrderItemNotFoundException("id-"+id);
		}
		return order;
	}
	
	public void deleteOrder(int id) {
		orderRepository.deleteById(id);
		return;
	}
	
	public void deleteAllOrder() {
		orderRepository.deleteAll();
		return;
	}

}
