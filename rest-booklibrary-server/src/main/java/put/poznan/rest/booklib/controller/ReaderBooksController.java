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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import put.poznan.rest.booklib.model.Book;
import put.poznan.rest.booklib.service.BookService;

@Controller
@RequestMapping("/readers/{id}/books")
public class ReaderBooksController {
	
	@Autowired
	private BookService bookService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Book> getBooksByReader(@PathVariable Integer id, 
			@RequestParam Map<String, String> params) {
		params.put("readerId", id.toString());
		return bookService.getBooks(params);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> returnAllBooks(@PathVariable Integer id) {
		bookService.returnAllBooks(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "{bookId}", method = RequestMethod.GET)
	public ResponseEntity<Book> getBookByReader(@PathVariable Integer id, @PathVariable Integer bookId) {
		Book book = bookService.getBook(bookId, null, id);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(book.getEtag());
		return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{bookId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> putBookByReader(@PathVariable Integer id, @PathVariable Integer bookId, 
			@RequestHeader(value="If-Match", required = false) String eTag) {
		HttpHeaders responseHeaders = new HttpHeaders();
		bookService.borrowBookByReader(bookId, id, eTag);
	    responseHeaders.setETag(eTag);
		return new ResponseEntity<Void>(responseHeaders, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "{bookId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBookByReader(@PathVariable Integer id, @PathVariable Integer bookId,
			@RequestHeader(value="If-Match", required = false) String eTag) {
		bookService.returnBookByReader(bookId, id, eTag);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
			reason="Given page or pageSize parameters are not valid numbers")
	@ExceptionHandler(NumberFormatException.class)
	public void wrongNumberFormat() {};
	
}
