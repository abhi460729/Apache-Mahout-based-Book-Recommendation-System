package bookmartapplication.main.controller;

import java.net.URI;
import java.util.Date;
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
import bookmartapplication.main.entities.Book;
import bookmartapplication.main.entityLayer.repo.BookEntityLayerService;
import bookmartapplication.main.exceptions.BookNotFoundException;
import bookmartapplication.main.utilities.Constants;


@Controller
@RequestMapping(path="/book")
public class BookEntityController{
	
	@Autowired
	private BookEntityLayerService bookEntityLayerService;

	@PostMapping("/create")
	public ResponseEntity<Object> createBook(@RequestBody Book book) {
		book.setPublished_date(new Date());
		Book save = bookEntityLayerService.createBook(book);
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(save.getB_id())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/readAll")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE)
    public @ResponseBody List<Book> readAllBooks() {
        return bookEntityLayerService.readAllBooks();
    }
	
	@GetMapping(path = "/read/{id}")
	@Cacheable(value=Constants.TEN_SECONDS_CACHE,key="'BookInCache'+#id")
	public @ResponseBody Optional<Book> readBook(@PathVariable int id) {
		Optional<Book> book = bookEntityLayerService.readBook(id);
		if(!book.isPresent()) {
			throw new BookNotFoundException("id-"+id);
		}
		return book;
	}
	
	@DeleteMapping("delete/{id}")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,key="'BookInCache'+#id") 
	public @ResponseBody void deleteBook(@PathVariable int id) {
		bookEntityLayerService.deleteBook(id);
		return;
	}
	
	@DeleteMapping("/delete")
	@CacheEvict(value = Constants.TEN_SECONDS_CACHE,allEntries = true)
	public @ResponseBody void deleteAllBooks() {
		bookEntityLayerService.deleteAllBooks();;
		return;
	}
	
}
