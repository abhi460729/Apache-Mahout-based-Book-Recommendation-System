package bookmartapplication.main.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmartapplication.main.entities.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	public List<Book> findByAuthor(String author);
	public List<Book> findByCategory(String category);
	public List<Book> findByName(String name);
}
