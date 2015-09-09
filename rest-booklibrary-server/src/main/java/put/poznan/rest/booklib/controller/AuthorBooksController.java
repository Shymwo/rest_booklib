package put.poznan.rest.booklib.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import put.poznan.rest.booklib.model.Book;
import put.poznan.rest.booklib.service.BookService;

@Controller
@RequestMapping("/authors/{id}/books")
public class AuthorBooksController {
	
	@Autowired
	private BookService bookService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Book> getBooksByAuthor(@PathVariable Integer id, 
			@RequestParam Map<String, String> params) {
		params.put("authorId", id.toString());
		return bookService.getBooks(params);
	}
	

	@RequestMapping(value = "{bookId}", method = RequestMethod.GET)
	public ResponseEntity<Book> getBookByAuthor(@PathVariable Integer id, @PathVariable Integer bookId) {
		Book book = bookService.getBook(bookId, id, null);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(book.getEtag());
		return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Book> postBookByAuthor(@PathVariable Integer id, @RequestBody Book book, 
			@RequestParam String token, UriComponentsBuilder b) {
		bookService.addBook(book, token, id);
		UriComponents location = b.path("/authors/{id}/books/{bookId}").buildAndExpand(id, book.getId());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(book.getEtag());
	    responseHeaders.setLocation(location.toUri());
		return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{bookId}", method = RequestMethod.PUT)
	public ResponseEntity<Book> putBookByAuthor(@PathVariable Integer id, @PathVariable Integer bookId, 
			@RequestBody Book book, @RequestHeader(value="If-Match", required = false) String eTag) {
		HttpHeaders responseHeaders = new HttpHeaders();
		book.setId(bookId);
		book.setEtag(eTag);
		bookService.updateBook(book, id);
	    responseHeaders.setETag(book.getEtag());
		return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "{bookId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBookByAuthor(@PathVariable Integer id, @PathVariable Integer bookId,
			@RequestHeader(value="If-Match", required = false) String eTag) {
		bookService.removeBook(bookId, eTag, id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
			reason="Given page or pageSize parameters are not valid numbers")
	@ExceptionHandler(NumberFormatException.class)
	public void wrongNumberFormat() {};
	
}
