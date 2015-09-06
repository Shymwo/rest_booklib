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
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Book> getBooks(@RequestParam Map<String, String> params) {
		return bookService.getBooks(params);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> getBook(@PathVariable Integer id) {
		Book book = bookService.getBook(id);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(book.getEtag());
		return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Book> postBook(@RequestBody Book book, @RequestParam String token, 
			UriComponentsBuilder b) {
		bookService.addBook(book, token);
		UriComponents location = b.path("/books/{id}").buildAndExpand(book.getId());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(book.getEtag());
	    responseHeaders.setLocation(location.toUri());
		return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<Book> putBook(@PathVariable Integer id, @RequestBody Book book,
			@RequestHeader(value="If-Match", required = false) String eTag) {
		HttpHeaders responseHeaders = new HttpHeaders();
		book.setId(id);
		book.setEtag(eTag);
		bookService.updateBook(book);
	    responseHeaders.setETag(book.getEtag());
		return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBook(@PathVariable Integer id, 
			@RequestHeader(value="If-Match", required = false) String eTag) {
		bookService.removeBook(id, eTag);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
			reason="Given page or pageSize parameters are not valid numbers")
	@ExceptionHandler(NumberFormatException.class)
	public void wrongNumberFormat() {};
	
}
