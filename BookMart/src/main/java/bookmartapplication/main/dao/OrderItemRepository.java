package bookmartapplication.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmartapplication.main.entities.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

}
