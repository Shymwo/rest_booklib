package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.model.Book;
import put.poznan.rest.booklib.util.FacesMessageUtil;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class BooksBean implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Book> books;
	
	private Book selectedBook;
	
	private List<Author> authors;
	
	private String titleLike;
	
	private String genreLike;
	
	private String authorNameLike;
	
	private String authorLastnameLike;
	
	@SuppressWarnings("unchecked")
	public void loadBooks() {
		try {
			String path = PropertyUtil.getProperty("rest.uri")
					+"/books?titleLike={titleLike}&genreLike={genreLike}"
					+ "&authorNameLike={authorNameLike}&authorLastnameLike={authorLastnameLike}";
			Map<String, String> urlVariables = new HashMap<String, String>();
			urlVariables.put("titleLike", titleLike);
			urlVariables.put("genreLike", genreLike);
			urlVariables.put("authorNameLike", authorNameLike);
			urlVariables.put("authorLastnameLike", authorLastnameLike);
			RestTemplate restTemplate = new RestTemplate();
			books = restTemplate.getForObject(path, List.class, urlVariables);
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadAuthors() {
		try {
			String path = PropertyUtil.getProperty("rest.uri")+"/authors";
			RestTemplate restTemplate = new RestTemplate();
			authors = restTemplate.getForObject(path, List.class);
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
		}
	}
	
	public void loadBook(Integer id) {
		try {
			selectedBook = null;
			String path = PropertyUtil.getProperty("rest.uri")+"/books/"+id;
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<Void> entity = new HttpEntity<Void>(new HttpHeaders());
			ResponseEntity<Book> response = restTemplate.exchange(
				    path, HttpMethod.GET, entity, Book.class);
			if(HttpStatus.OK.value() != response.getStatusCode().value()) {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return;
			}
			selectedBook = response.getBody();
			selectedBook.setEtag(response.getHeaders().getETag());
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
		}
	}
	
	public void loadNewBook() {
		selectedBook = new Book();
		selectedBook.setAuthor(new Author());
	}
	
	public String createToken() {
		try {
			String tokenPath = PropertyUtil.getProperty("rest.uri")+"/tokens";
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<Void> entity = new HttpEntity<Void>(new HttpHeaders());
			ResponseEntity<String> response = restTemplate.exchange(
				    tokenPath, HttpMethod.POST, entity, String.class);
			if (HttpStatus.CREATED.value() != response.getStatusCode().value()) {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
			return response.getBody();
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
			return null;
		}
	}
	
	public String addBook(String token) {
		try {
			String path = PropertyUtil.getProperty("rest.uri")+"/books?token="+token;
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<Book> entity = new HttpEntity<Book>(selectedBook, new HttpHeaders());
			ResponseEntity<Book> response = restTemplate.exchange(
				    path, HttpMethod.POST, entity, Book.class);
			if (HttpStatus.CREATED.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
		} catch (HttpClientErrorException e) {
			FacesMessageUtil.showError(e);
			return null;
		}
		return "return";
	}
	
	public String updateBook() {
		try {
			String path = PropertyUtil.getProperty("rest.uri")+"/books/"+selectedBook.getId();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("If-Match", selectedBook.getEtag());
			HttpEntity<Book> entity = new HttpEntity<Book>(selectedBook, headers);
			ResponseEntity<Book> response = restTemplate.exchange(
				    path, HttpMethod.PUT, entity, Book.class);
			if (HttpStatus.ACCEPTED.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
			return null;
		}
		return "return";
	}
	
	public String deleteBook() {
		try {
			String path = PropertyUtil.getProperty("rest.uri")+"/books/"+selectedBook.getId();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("If-Match", selectedBook.getEtag());
			HttpEntity<Void> entity = new HttpEntity<Void>(headers);
			ResponseEntity<Void> response = restTemplate.exchange(
				    path, HttpMethod.DELETE, entity, Void.class);
			if (HttpStatus.NO_CONTENT.value() == response.getStatusCode().value()) {
				FacesMessageUtil.showSuccessInfo();
			} else {
				FacesMessageUtil.showInvalidStatusWarn(response.getStatusCode());
				return null;
			}
			
		} catch (Exception e) {
			FacesMessageUtil.showError(e);
			if (!e.getMessage().contains(Integer.toString(HttpStatus.NOT_FOUND.value()))) {
				return null;
			}
		}
		return "return";
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Book getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(Book selectedBook) {
		this.selectedBook = selectedBook;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public String getGenreLike() {
		return genreLike;
	}

	public void setGenreLike(String genreLike) {
		this.genreLike = genreLike;
	}

	public String getAuthorNameLike() {
		return authorNameLike;
	}

	public void setAuthorNameLike(String authorNameLike) {
		this.authorNameLike = authorNameLike;
	}

	public String getAuthorLastnameLike() {
		return authorLastnameLike;
	}

	public void setAuthorLastnameLike(String authorLastnameLike) {
		this.authorLastnameLike = authorLastnameLike;
	}

}