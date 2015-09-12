package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Reader;
 
public class ReadersBean implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Reader> readers;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		String uri = "http://localhost:8080/rest-booklibrary-server/readers";
		RestTemplate restTemplate = new RestTemplate();
		readers = restTemplate.getForObject(uri, List.class);
	}
	
	public List<Reader> getReaders() {
		return readers;
	}

}