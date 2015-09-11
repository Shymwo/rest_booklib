package put.poznan.rest.booklib.dao;

import java.util.List;
import java.util.Map;

import put.poznan.rest.booklib.model.Book;

public interface BookDao {
	
	public void addBook(Book book);
	public void updateBook(Book book);
	public void removeBook(Book book);
	public Book getBook(Integer id);
	public List<Book> getBooks(Map<String, String> params);
	public void returnAllBooks(Integer readerId);

}
