package put.poznan.rest.booklib.service;

import java.util.List;
import java.util.Map;

import put.poznan.rest.booklib.model.Author;

public interface AuthorService {
	
	public void addAuthor(Author author, String token);
	public void updateAuthor(Author author);
	public void removeAuthor(Integer id, String eTag);
	public Author getAuthor(Integer id);
	public List<Author> getAuthors(Map<String, String> params);
	
}
