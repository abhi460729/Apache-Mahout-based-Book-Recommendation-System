package bookmartapplication.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bookmartapplication.main.entities.Orders;
@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer>{

}
