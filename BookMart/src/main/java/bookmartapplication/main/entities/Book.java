package bookmartapplication.main.entities;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="book")
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int b_id;
	@Column(name="b_name")
	private String name;
	private String author;
	private int price;
	private String version;
	private String category;
	private int book_number;
	private Date published_date;

	public int getB_id() {
		return b_id;
	}
	

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCategory() {
		return category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getBook_number() {
		return book_number;
	}
	public void setBook_number(int book_number) {
		this.book_number = book_number;
	}
	public Date getPublished_date() {
		return published_date;
	}
	public void setPublished_date(Date published_date) {
		this.published_date = published_date;
	}


	@Override
	public String toString() {
		return "Book [b_id=" + b_id + ", name=" + name + ", author=" + author + ", price=" + price + ", version="
				+ version + ", category=" + category + ", book_number=" + book_number + ", published_date="
				+ published_date + "]";
	}

	

	

	

	
	
	
	
	
	
	
}
