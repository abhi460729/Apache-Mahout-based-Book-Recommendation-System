package bookmartapplication.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookmartapplication.main.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
