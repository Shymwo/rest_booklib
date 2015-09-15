package put.poznan.rest.booklib.bean;

import java.io.Serializable;

import put.poznan.rest.booklib.util.PropertyUtil;
 
public class AuthorBooksBean extends BooksBean implements Serializable {
	
	private static final long serialVersionUID = -5347834724768856047L;
	
	private String authorId;

	@Override
	public String getUrl() {
		return PropertyUtil.getProperty("rest.uri")+"/authors/"+authorId+"/books";
	}

	@Override
	public String getUrlForLoadRecords() {
		return PropertyUtil.getProperty("rest.uri")+"/authors/"+authorId+"/books";
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

}