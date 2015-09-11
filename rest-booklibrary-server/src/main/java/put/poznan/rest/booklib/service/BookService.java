package put.poznan.rest.booklib.service;

import java.util.List;
import java.util.Map;

import put.poznan.rest.booklib.model.Book;

public interface BookService {
	
	public void addBook(Book book, String token);
	public void addBook(Book book, String token, Integer authorId);
	public void updateBook(Book book);
	public void updateBook(Book book, Integer authorId);
	public void removeBook(Integer id, String eTag);
	public void removeBook(Integer id, String eTag, Integer authorId);
	public Book getBook(Integer id);
	public Book getBook(Integer id, Integer authorId, Integer readerId);
	public List<Book> getBooks(Map<String, String> params);
	public void borrowBookByReader(Integer id, Integer readerId, String eTag);
	public void returnBookByReader(Integer id, Integer readerId, String eTag);
	public void returnAllBooks(Integer readerId);
	
}
