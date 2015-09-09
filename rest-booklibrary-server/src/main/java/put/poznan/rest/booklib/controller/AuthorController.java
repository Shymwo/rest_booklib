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

import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.service.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Author> getAuthors(@RequestParam Map<String, String> params) {
		return authorService.getAuthors(params);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Author> getAuthor(@PathVariable Integer id) {
		Author author = authorService.getAuthor(id);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(author.getEtag());
		return new ResponseEntity<Author>(author, responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Author> postAuthor(@RequestBody Author author, @RequestParam String token, 
			UriComponentsBuilder b) {
		authorService.addAuthor(author, token);
		UriComponents location = b.path("/authors/{id}").buildAndExpand(author.getId());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(author.getEtag());
	    responseHeaders.setLocation(location.toUri());
		return new ResponseEntity<Author>(author, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<Author> putAuthor(@PathVariable Integer id, @RequestBody Author author,
			@RequestHeader(value="If-Match", required = false) String eTag) {
		HttpHeaders responseHeaders = new HttpHeaders();
		author.setId(id);
		author.setEtag(eTag);
		authorService.updateAuthor(author);
	    responseHeaders.setETag(author.getEtag());
		return new ResponseEntity<Author>(author, responseHeaders, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id, 
			@RequestHeader(value="If-Match", required = false) String eTag) {
		authorService.removeAuthor(id, eTag);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
			reason="Given page or pageSize parameters are not valid numbers")
	@ExceptionHandler(NumberFormatException.class)
	public void wrongNumberFormat() {};
	
}
