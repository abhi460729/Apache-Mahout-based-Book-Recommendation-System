package bookmartapplication.main.businessLayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bookmartapplication.main.entities.Book;
import bookmartapplication.main.entities.Cart;
import bookmartapplication.main.entities.OrderItem;
import bookmartapplication.main.entities.Orders;
import bookmartapplication.main.entities.Rating;
import bookmartapplication.main.entities.User;
import bookmartapplication.main.entityLayer.repo.BookEntityLayerService;
import bookmartapplication.main.entityLayer.repo.CartEntityLayerService;
import bookmartapplication.main.entityLayer.repo.OrderEntityLayerService;
import bookmartapplication.main.entityLayer.repo.OrderItemEntityLayerService;
import bookmartapplication.main.entityLayer.repo.RatingEntityLayerService;
import bookmartapplication.main.entityLayer.repo.UserEntityLayerService;

@Service
@Transactional
public class BusinessLayerImpl implements BusinessLayerRepo{
	
	@Autowired
	private UserEntityLayerService userEntityLayerService;
	
	@Autowired
	private OrderItemEntityLayerService orderItemEntityLayerService;
	
	@Autowired
	private CartEntityLayerService cartEntityLayerService;
	
	@Autowired
	private BookEntityLayerService bookEntityLayerService;
	
	@Autowired
	private RatingEntityLayerService ratingEntityLayerService;
	
	@Autowired
	private OrderEntityLayerService orderEntityLayerService;
	
	

	public Book uploadBook(Book book){
		Book createBook = bookEntityLayerService.createBook(book);
		return createBook;
	}
	
	public List<Orders> getOrders(){
		List<Orders> readAllOrder = orderEntityLayerService.readAllOrder();
		return readAllOrder;
	}
	
	public void deleteBook(int id){
		List<Rating> readAllRatings = ratingEntityLayerService.readAllRatings();
		readAllRatings.stream().filter(bookrating->bookrating.getBook().getB_id()==id).forEach(bookrating->ratingEntityLayerService.deleteRating(bookrating.getR_id()));
		bookEntityLayerService.deleteBook(id);
		return;
	}
	
	public Book fetchBook(int  bookid){
		Optional<Book> createBook = bookEntityLayerService.readBook(bookid);
		return createBook.get();
	}
	
	public List<Book> fetchAllBooks(){
		List<Book> createBook = bookEntityLayerService.readAllBooks();
		return createBook;
	}
	
	public User registerUser(User user) {
		String email=user.getEmail();
		String password=user.getPassword();
		List<User> readAllUsers = userEntityLayerService.readAllUsers();
		Optional<User> existingUser = readAllUsers.stream().filter(myuser->myuser.getEmail().equals(email) && user.getPassword().equals(password)).findAny();
		if(!existingUser.isPresent()) {
			User createUser = userEntityLayerService.createUser(user);
			return createUser;
		}
		else {
			return null;
		}
	}
	
	public Orders createOrderandCheckOut(int userid){
		List<Cart> allCarts = cartEntityLayerService.readAllCart();
		Optional<Cart> myCart = allCarts.stream().filter(cart->cart.getUser().getId()==userid).findAny();
		List<OrderItem> readAllOrderItem = orderItemEntityLayerService.readAllOrderItem();
		List<OrderItem> collectedItems = readAllOrderItem.stream().filter(myOrderItem->myOrderItem.getCart().getCart_id()==myCart.get().getCart_id() && myOrderItem.getQuantity()>0).collect(Collectors.toList());
		Orders order = new Orders();
		order.setOrderItem(collectedItems);
		Orders createdOrder = orderEntityLayerService.createOrder(order);
		readAllOrderItem.stream().filter(myOrderItem->myOrderItem.getCart().getCart_id()==myCart.get().getCart_id()).forEach(myOrderItem->myOrderItem.setCart(null));
		return createdOrder;
	}
		
	public Optional<User> loginUser(String email, String password) {
		List<User> users = userEntityLayerService.readAllUsers();
		Optional<User> loggedInUser = users.stream().
			    filter(user -> user.getPassword().equals(password)).filter(user -> user.getEmail().equals(email))
			    .findFirst();
		return loggedInUser;
	}
	
	public List<Book> searchBooks(String name, String category, String author) {
		List<Book> readAllBooksByCategory= new ArrayList<Book>();
		List<Book> readAllBooksByName= new ArrayList<Book>();
		List<Book> readAllBooksByAuthor=new ArrayList<Book>();
		List<Book> finalList= new ArrayList<Book>();
			if(category!=null) {
			readAllBooksByCategory = bookEntityLayerService.readAllBooksByCategory(category);
			}
			if(name!=null) {
			readAllBooksByName = bookEntityLayerService.readAllBooksByName(name);
			}
			if(author!=null) {
			readAllBooksByAuthor = bookEntityLayerService.readAllBooksByAuthor(author);
			}
			finalList = Stream.of(readAllBooksByCategory, readAllBooksByName, readAllBooksByAuthor)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
			return finalList;
	}
	
	
	public OrderItem createOrderItemForCart(OrderItem orderitem, int userid) {
		OrderItem createdOrderItem = orderItemEntityLayerService.createOrderItem(orderitem);
		List<Cart> readCart = cartEntityLayerService.readAllCart();
		Optional<Cart> myCart = readCart.stream().filter(cart->cart.getUser().getId()==userid).findAny();
		createdOrderItem.setCart(myCart.get());
		return createdOrderItem;
	}
	
	public Cart createCart(int id) {
		Optional<User> readUser = userEntityLayerService.readUser(id);
		Cart cart= new Cart();
		cart.setUser(readUser.get());
		Cart createdCart = cartEntityLayerService.createCart(cart);
		return createdCart;
	}
	
	public void removeFromCart(int userid,int bookid) {
			List<Cart> allCarts = cartEntityLayerService.readAllCart();
			Optional<Cart> myCart = allCarts.stream().filter(cart->cart.getUser().getId()==userid).findAny();
			List<OrderItem> readAllOrderItem = orderItemEntityLayerService.readAllOrderItem();
			readAllOrderItem.stream().filter(myOrderItem->myOrderItem.getCart()!=null).filter(myOrderItem->myOrderItem.getCart().getCart_id()==myCart.get().getCart_id() && myOrderItem.getBook().getB_id()==bookid && myOrderItem.getQuantity()>0).forEach(myOrderItem->orderItemEntityLayerService.deleteOrderItem(myOrderItem.getOrderitem_id()));
			return;
	}
	
	public int totalAmountandPlaceOrder(int userid) {
		int total=0;
		List<Cart> allCarts = cartEntityLayerService.readAllCart();
		Optional<Cart> myCart = allCarts.stream().filter(cart->cart.getUser().getId()==userid).findAny();
		List<OrderItem> readAllOrderItem = orderItemEntityLayerService.readAllOrderItem();
		total = readAllOrderItem.stream().filter(myOrderItem->myOrderItem.getCart()!=null).filter(myOrderItem->myOrderItem.getCart().getCart_id()==myCart.get().getCart_id()).mapToInt(myOrderItem->myOrderItem.getQuantity()*myOrderItem.getBook().getPrice()).sum();
		return total;
	}
	
	public void deleteUser(int id) {
		List<Cart> allCarts = cartEntityLayerService.readAllCart();
		Optional<Cart> myCart = allCarts.stream().filter(cart->cart.getUser().getId()==id).findAny();
		List<OrderItem> readAllOrderItem = orderItemEntityLayerService.readAllOrderItem();
		readAllOrderItem.stream().filter(myOrderItem->myOrderItem.getCart()!=null).filter(myOrderItem->myOrderItem.getCart().getCart_id()==myCart.get().getCart_id()).forEach(myOrderItem->orderItemEntityLayerService.deleteOrderItem(myOrderItem.getOrderitem_id()));
		cartEntityLayerService.deleteCart(myCart.get().getCart_id());
		userEntityLayerService.deleteUser(id);
		return;
	}
	
	
	public void deleteUserAccount(int id, HttpSession session) {
		deleteUser(id);
		session.invalidate();
		return;
	}
	
	public Rating ratingSubmit(Rating rating) {
		Rating createdRating=ratingEntityLayerService.createRating(rating);
		return createdRating;
	}

	
	public List<User> fetchAllUsers() {
		List<User> readAllUsers = userEntityLayerService.readAllUsers();
		return readAllUsers;
	}
	
	
	
	
}
