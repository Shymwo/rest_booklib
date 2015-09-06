package put.poznan.rest.booklib.dao;

import java.util.List;

import put.poznan.rest.booklib.model.Author;

public interface AuthorDao {
	
	public void addAuthor(Author author);
	public void updateAuthor(Author author);
	public void removeAuthor(Integer id);
	public Author getAuthor(Integer id);
	public List<Author> getAllAuthors();

}
