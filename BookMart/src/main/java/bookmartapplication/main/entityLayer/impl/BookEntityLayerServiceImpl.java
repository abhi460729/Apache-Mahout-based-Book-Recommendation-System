package bookmartapplication.main.entityLayer.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bookmartapplication.main.dao.BookRepository;
import bookmartapplication.main.entities.Book;
import bookmartapplication.main.entityLayer.repo.BookEntityLayerService;
import bookmartapplication.main.exceptions.BookNotFoundException;

@Service
public class BookEntityLayerServiceImpl implements BookEntityLayerService{
	@Autowired
	private BookRepository bookRepository;

	public Book createBook(Book book) {
		book.setPublished_date(new Date());
		Book save = bookRepository.save(book);
		return save;
	}
	
    public List<Book> readAllBooks() {
        return bookRepository.findAll();
    }
	
	public Optional<Book> readBook(int id) {
		Optional<Book> book = bookRepository.findById(id);
		if(!book.isPresent()) {
			throw new BookNotFoundException("id-"+id);
		}
		return book;
	}
	
	public void deleteBook(int id) {
		bookRepository.deleteById(id);
		return;
	}
	
	public void deleteAllBooks() {
		bookRepository.deleteAll();
		return;
	}
	
	public List<Book> readAllBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }
	
	public List<Book> readAllBooksByName(String name) {
        return bookRepository.findByName(name);
    }
	
	public List<Book> readAllBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
	
	
}
