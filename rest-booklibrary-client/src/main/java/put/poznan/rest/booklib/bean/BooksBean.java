package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.model.Book;
import put.poznan.rest.booklib.util.FacesMessageUtil;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class BooksBean extends CommonBean<Book> implements Serializable {
     
	private static final long serialVersionUID = -4642774537753298469L;
	
	private List<Author> authors;
	
	private String titleLike;
	
	private String genreLike;
	
	private String authorNameLike;
	
	private String authorLastnameLike;
	
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
	
	@Override
	public void loadNewRecord() {
		setSelectedRecord(new Book());
		getSelectedRecord().setAuthor(new Author());
	}

	@Override
	public String getUrl() {
		return PropertyUtil.getProperty("rest.uri")+"/books";
	}

	@Override
	public Map<String, String> getVarsForLoadRecords() {
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("titleLike", titleLike);
		urlVariables.put("genreLike", genreLike);
		urlVariables.put("authorNameLike", authorNameLike);
		urlVariables.put("authorLastnameLike", authorLastnameLike);
		return urlVariables;
	}

	@Override
	public String getUrlForLoadRecords() {
		return PropertyUtil.getProperty("rest.uri")
				+"/books?titleLike={titleLike}&genreLike={genreLike}"
				+ "&authorNameLike={authorNameLike}&authorLastnameLike={authorLastnameLike}";
	}

	@Override
	public Class<Book> getRecordClass() {
		return Book.class;
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