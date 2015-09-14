package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class AuthorsBean implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Author> authors;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		String path = PropertyUtil.getProperty("rest.uri")+"/authors";
		RestTemplate restTemplate = new RestTemplate();
		authors = restTemplate.getForObject(path, List.class);
	}
	
	public List<Author> getAuthors() {
		return authors;
	}

}