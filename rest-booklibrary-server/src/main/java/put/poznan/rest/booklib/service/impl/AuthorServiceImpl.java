package put.poznan.rest.booklib.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import put.poznan.rest.booklib.dao.AuthorDao;
import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	@Autowired
	private AuthorDao authorDao;

	@Transactional
	public void addAuthor(Author author) {
		authorDao.addAuthor(author);
	}

	@Transactional
	public void updateAuthor(Author author) {
		authorDao.updateAuthor(author);
	}

	@Transactional
	public void removeAuthor(Integer id) {
		authorDao.removeAuthor(id);
	}

	@Transactional
	public Author getAuthor(Integer id) {
		return authorDao.getAuthor(id);
	}

	@Transactional
	public List<Author> getAllAuthors() {
		return authorDao.getAllAuthors();
	}

}
