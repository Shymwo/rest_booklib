package put.poznan.rest.booklib.bean;

import java.io.Serializable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Book;
import put.poznan.rest.booklib.util.FacesMessageUtil;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class ReaderBooksBean extends BooksBean implements Serializable {
	
	private static final long serialVersionUID = -3493960194915816772L;
	
	private String readerId;
	
	public void updateRecord(Book book) {
		try {
			String path = getUrl()+"/"+book.getId();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("If-Match", book.getEtag());
			HttpEntity<Void> entity = new HttpEntity<Void>(headers);
			ResponseEntity<Void> response = restTemplate.exchange(
				    path, HttpMethod.PUT, entity, Void.class);
			if (HttpStatus.ACCEPTED.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
			}
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
		}
	}
	
	public void deleteRecord(Integer id) {
		super.loadRecord(id);
		super.deleteRecord();
	}
	
	public void deleteAll() {
		try {
			String path = getUrl();
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<Void> entity = new HttpEntity<Void>(new HttpHeaders());
			ResponseEntity<Void> response = restTemplate.exchange(
				    path, HttpMethod.DELETE, entity, Void.class);
			if (HttpStatus.NO_CONTENT.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
			}
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
		}
	}

	@Override
	public String getUrl() {
		return PropertyUtil.getProperty("rest.uri")+"/readers/"+readerId+"/books";
	}

	@Override
	public String getUrlForLoadRecords() {
		return PropertyUtil.getProperty("rest.uri")+"/readers/"+readerId+"/books";
	}

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

}