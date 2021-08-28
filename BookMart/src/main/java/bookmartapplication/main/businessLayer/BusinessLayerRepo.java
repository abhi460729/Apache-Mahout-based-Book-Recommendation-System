package bookmartapplication.main.businessLayer;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import bookmartapplication.main.entities.Book;
import bookmartapplication.main.entities.Cart;
import bookmartapplication.main.entities.OrderItem;
import bookmartapplication.main.entities.Orders;
import bookmartapplication.main.entities.Rating;
import bookmartapplication.main.entities.User;

public interface BusinessLayerRepo {
	public Rating ratingSubmit(Rating rating);
	public Book uploadBook(Book book);
	public Book fetchBook(int  bookid);
	public User registerUser(User user);
	public int totalAmountandPlaceOrder(int id);
	public Optional<User> loginUser(String email, String password);
	public List<Book> searchBooks(String name, String category, String author);
	public OrderItem createOrderItemForCart(OrderItem orderitem, int userId);
	public void removeFromCart(int userid, int bookid); 
	public Cart createCart(int userid);
	public void deleteUser(int id);
	public void deleteBook(int id);
	public void deleteUserAccount(int id, HttpSession session);
	public List<Book> fetchAllBooks();
	public List<User> fetchAllUsers();
	public Orders createOrderandCheckOut(int userid);
	public List<Orders> getOrders();
}
