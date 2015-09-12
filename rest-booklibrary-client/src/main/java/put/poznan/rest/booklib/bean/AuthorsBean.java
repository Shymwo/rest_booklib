package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Author;
 
public class AuthorsBean implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Author> authors;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		String uri = "http://localhost:8080/rest-booklibrary-server/authors";
		RestTemplate restTemplate = new RestTemplate();
		authors = restTemplate.getForObject(uri, List.class);
	}
	
	public List<Author> getAuthors() {
		return authors;
	}

}