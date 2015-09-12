package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Book;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class BooksBean implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Book> books;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		String path = PropertyUtil.getProperty("rest.uri")+"/books";
		RestTemplate restTemplate = new RestTemplate();
		books = restTemplate.getForObject(path, List.class);
	}
	
	public List<Book> getBooks() {
		return books;
	}

}