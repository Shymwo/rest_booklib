package put.poznan;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Book;
 
public class BooksBean implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Book> books;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		String uri = "http://localhost:8080/rest-booklibrary-server/books";
		RestTemplate restTemplate = new RestTemplate();
		books = restTemplate.getForObject(uri, List.class);
	}
	
	public List<Book> getBooks() {
		return books;
	}

}