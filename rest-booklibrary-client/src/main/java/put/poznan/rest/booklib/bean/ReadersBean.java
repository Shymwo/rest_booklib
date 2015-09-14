package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Reader;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class ReadersBean implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Reader> readers;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		String path = PropertyUtil.getProperty("rest.uri")+"/readers";
		RestTemplate restTemplate = new RestTemplate();
		readers = restTemplate.getForObject(path, List.class);
	}
	
	public List<Reader> getReaders() {
		return readers;
	}

}