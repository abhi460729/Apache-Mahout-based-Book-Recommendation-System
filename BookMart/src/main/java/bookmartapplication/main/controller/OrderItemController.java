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
import bookmartapplication.main.entities.OrderItem;
import bookmartapplication.main.entityLayer.repo.OrderItemEntityLayerService;
import bookmartapplication.main.exceptions.OrderItemNotFoundException;
import bookmartapplication.main.utilities.Constants;

@Controller
@RequestMapping(path="/orderitem")
public class OrderItemController {
	
	@Autowired
	private OrderItemEntityLayerService orderItemEntityLayerService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createOrderItem(@RequestBody OrderItem order) {
		OrderItem savedOrder=orderItemEntityLayerService.createOrderItem(order);
		
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedOrder.getOrderitem_id())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/readAll")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
    public @ResponseBody List<OrderItem> readAllOrderItem() {
        return orderItemEntityLayerService.readAllOrderItem();
    }
	
	@GetMapping(path = "/read/{id}")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE,key="'OrderItemInCache'+#id")
	public @ResponseBody Optional<OrderItem> readOrderItem(@PathVariable int id) {
		Optional<OrderItem> order = orderItemEntityLayerService.readOrderItem(id);
		if(!order.isPresent()) {
			throw new OrderItemNotFoundException("id-"+id);
		}
		return order;
	}
	
	@DeleteMapping("delete/{id}")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,key="'OrderItemInCache'+#id")
	public @ResponseBody void deleteOrderItem(@PathVariable int id) {
		orderItemEntityLayerService.deleteOrderItem(id);
		return;
	}
	
	@DeleteMapping("/delete")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,allEntries = true)
	public @ResponseBody void deleteAllOrderItem() {
		orderItemEntityLayerService.deleteAllOrderItem();
		return;
	}
	
}
