package bookmartapplication.main.entityLayer.repo;

import java.util.List;
import java.util.Optional;
import bookmartapplication.main.entities.Book;

public interface BookEntityLayerService {
	public Book createBook(Book book);
    public List<Book> readAllBooks();
	public Optional<Book> readBook(int id);
	public void deleteBook(int id);
	public void deleteAllBooks();
	public List<Book> readAllBooksByCategory(String category);
	public List<Book> readAllBooksByName(String name);
	public List<Book> readAllBooksByAuthor(String author);
}
