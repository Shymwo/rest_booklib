package put.poznan.rest.booklib.service;

import java.util.List;
import java.util.Map;

import put.poznan.rest.booklib.model.Book;

public interface BookService {
	
	public Book addBook(Book book, String token);
	public Book addBook(Book book, String token, Integer authorId);
	public void updateBook(Book book);
	public void updateBook(Book book, Integer authorId, Integer readerId);
	public void removeBook(Integer id, String eTag);
	public void removeBook(Integer id, String eTag, Integer authorId, Integer readerId);
	public Book getBook(Integer id);
	public Book getBook(Integer id, Integer authorId, Integer readerId);
	public List<Book> getBooks(Map<String, String> params);
	
}
