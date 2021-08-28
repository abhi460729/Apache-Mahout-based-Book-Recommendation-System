package bookmartapplication.main.businessLayer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.recommendationengine.RecommenderSystem;
import bookmartapplication.main.entities.Book;
import bookmartapplication.main.entities.Cart;
import bookmartapplication.main.entities.OrderItem;
import bookmartapplication.main.entities.Orders;
import bookmartapplication.main.entities.Rating;
import bookmartapplication.main.entities.User;
import bookmartapplication.main.utilities.Constants;


@Controller
@RequestMapping(path="/bookmart")
public class BusinessLayerController {
	
	@Autowired
	private BusinessLayerRepo businessLayerRepo;
	
	@PostMapping("/orderitem/create")
	public String createOrderItem(@ModelAttribute OrderItem orderItem, Model model, HttpSession session) {
		int userId=(int) session.getAttribute("userId");
		OrderItem orderitem= new OrderItem();
		int id = orderItem.getBook().getB_id();
		Book fetchdBook = businessLayerRepo.fetchBook(id);
		orderitem.setBook(fetchdBook);
		orderitem.setQuantity(orderItem.getQuantity());
		OrderItem createOrderItemForCart= businessLayerRepo.createOrderItemForCart(orderitem,userId);
		return "user-dashboard";
		//DONE
	}
	
	@PostMapping("/orderitem/delete")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE)
	public String removeItemFromCart(@ModelAttribute OrderItem orderitem,HttpSession session) {
		int userid=(int) session.getAttribute("userId");
		int bookid=orderitem.getBook().getB_id();
		businessLayerRepo.removeFromCart(userid,bookid);
		return "user-cart-dashboard";
		//DONE
	}
	
	
	@GetMapping(path = "/user/total")
	public String totalAmountandPlaceOrder(HttpSession session, Model model) {
		int id=(int) session.getAttribute("userId");
		int totalAmount = businessLayerRepo.totalAmountandPlaceOrder(id);
		model.addAttribute("total", totalAmount);
		return "user-cart-dashboard";
		//DONE
	}
	
	@GetMapping(path = "/placeorder")
	public String checkOut(HttpSession session, Model model) {
		Object attribute = session.getAttribute("userId");
		if(attribute!=null) {
		int id=(int) session.getAttribute("userId");
		int totalAmount = businessLayerRepo.totalAmountandPlaceOrder(id);
		businessLayerRepo.createOrderandCheckOut(id);
		return "user-dashboard";
		}
		else {
			return "index";
		}
		//DONE
	}
	
	@GetMapping(path="/user/delete")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE) 
	public String deleteUser(HttpSession session) {
			int id=(int) session.getAttribute("userId");
			businessLayerRepo.deleteUserAccount(id,session);
			return "index";
			//DONE
	}
	
	@GetMapping("/cart/create")
	public String createCart(HttpSession session) {
		int userid=(int) session.getAttribute("userId");
		Cart createdCart = businessLayerRepo.createCart(userid);
		return "user-dashboard";
		//DONE
	}
	
	@GetMapping(path = "/register")
	public String signup() {
			return "registration-form";
			//DONE
	}
	
	@GetMapping(path = "/getOrders")
	public @ResponseBody List<Orders> getOrders() {
			List<Orders> orders = businessLayerRepo.getOrders();
			return orders;
			//DONE
	}
	
	@GetMapping(path = "/rating")
	public String rating() {
			return "rating-form";
			//DONE
	}
	
	@PostMapping(path = "/rating-submit")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
	public String ratingsubmit(@ModelAttribute Rating rating, Model model, HttpSession session) {
		int userid=(int) session.getAttribute("userId");
		rating.setUser_id(userid);
		Rating createdRating=businessLayerRepo.ratingSubmit(rating);
		return "user-dashboard";
		//DONE
	}
	
	@GetMapping(path = "/index")
	public String index() {
			return "index";
			//DONE
	}
	
	@PostMapping(path = "/registration-submit")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
	public String registrationSuccess(@ModelAttribute User user, Model model) {
			User registerUser = businessLayerRepo.registerUser(user);
			if(registerUser!=null) {
				model.addAttribute("user", registerUser);
				return "login-form";
			}
			else {
				return "registration-failed";
			}
			//DONE
	}
	
	@GetMapping(path = "/login")
	public String login(HttpSession session) {
			Object sessionId = session.getAttribute("userId");
			if(sessionId!=null) {
				return "user-dashboard";
			}
			return "login-form";
			//DONE
	}
	
	@GetMapping(path = "/deleteitem")
	public String deleteitem() {
			return "deleteitem-page";
			//DONE
	}
	
	@PostMapping(path = "/login-submit")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
	public String loginSuccess(@ModelAttribute User user, Model model, HttpSession session) {
			String password=user.getPassword();
			String email=user.getEmail();
			Optional<User> loggedInUser = businessLayerRepo.loginUser(email, password);
			if(loggedInUser.isPresent()) {
			session.setAttribute("userId", loggedInUser.get().getId());
			model.addAttribute("user", loggedInUser);
			return "user-dashboard";
			}
			else {
				return "login-failed";
			}
			//DONE
	}
	
	@PostMapping("/search-submit")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
    public String search(@ModelAttribute Book book, Model model) {
		String name=book.getName();
		String category=book.getCategory();
		String author=book.getAuthor();
        List<Book> searchedBooks = businessLayerRepo.searchBooks(name, category, author);
        model.addAttribute("book", searchedBooks);
        return "search-result";
        //DONE
    }
	
	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
		//DONE
	}
	
	@GetMapping(path = "/uploadbook")
	public String uploadbook() {
			return "upload-book";
			//DONE
	}

	
	@GetMapping(path = "/proceed")
	public String proceed() {
			return "user-cart-dashboard";
			//DONE
	}
	
	@GetMapping(path = "/deleteBookPage")
	public String deleteBookPage() {
			return "delete-book";
			//DONE
	}
	
	
	
	@PostMapping("/deleteBook")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
    public String deleteBook(@ModelAttribute Book book, Model model) {
		int id=book.getB_id();
		businessLayerRepo.deleteBook(id);
        return "index";
        //DONE
    }
	
	@PostMapping("/book/upload")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
	public String uploadBook(@ModelAttribute Book book, Model model) {
		Book uploadedBook = businessLayerRepo.uploadBook(book);
		model.addAttribute("book", uploadedBook);
		return "index";
		//DONE
	}
	
	@GetMapping(path = "/recommend")
	public String fetchAllBooks(HttpSession session, Model model) {
		int id=(int) session.getAttribute("userId");
		List<Book> fetchAllBooks = businessLayerRepo.fetchAllBooks();
		List<User> fetchAllUsers = businessLayerRepo.fetchAllUsers();
		try {
			List<String> recommendedBooks = RecommenderSystem.recommendBooks(fetchAllBooks,id);
			List<String> recommendedUsers = RecommenderSystem.recommendUsers(fetchAllUsers,id);
			model.addAttribute("recommendedbooks", recommendedBooks);
			model.addAttribute("recommendedusers", recommendedUsers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "user-dashboard";
		//DONE
	}

}
