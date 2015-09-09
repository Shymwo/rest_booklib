package put.poznan.rest.booklib.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import put.poznan.rest.booklib.dao.AuthorDao;
import put.poznan.rest.booklib.exception.AuthorValidationException;
import put.poznan.rest.booklib.exception.ContentNotFoundException;
import put.poznan.rest.booklib.exception.ETagInvalidException;
import put.poznan.rest.booklib.exception.ETagNotProvidedException;
import put.poznan.rest.booklib.exception.TokenInvalidException;
import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.service.AuthorService;
import put.poznan.rest.booklib.util.TokenUtil;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	@Autowired
	private AuthorDao authorDao;

	@Autowired
	private TokenUtil tokenUtil;

	@Transactional
	public void addAuthor(Author author, String token) {
		validateRequiredFields(author);
		if (tokenUtil.usePostToken(token)) {
			author.setId(null);
			author.setEtag(tokenUtil.getNewETag());
			authorDao.addAuthor(author);
		} else {
			throw new TokenInvalidException();
		}
	}

	@Transactional
	public void updateAuthor(Author author) {
		validateRequiredFields(author);
		Author temp = getAuthor(author.getId());
		if (author.getEtag() == null) {
			throw new ETagNotProvidedException(temp.getEtag());
		}
		if (!author.getEtag().equals(temp.getEtag())) {
			throw new ETagInvalidException(temp.getEtag());
		}
		author.setEtag(tokenUtil.getNewETag());
		authorDao.updateAuthor(author);
	}

	@Transactional
	public void removeAuthor(Integer id, String eTag) {
		Author author = getAuthor(id);
		if (eTag == null) {
			throw new ETagNotProvidedException(author.getEtag());
		}
		if (!eTag.equals(author.getEtag())) {
			throw new ETagInvalidException(author.getEtag());
		}
		authorDao.removeAuthor(author);
	}

	@Transactional
	public Author getAuthor(Integer id) {
		Author author = authorDao.getAuthor(id);
		if (author == null) {
			throw new ContentNotFoundException();
		}
		return author;
	}

	@Transactional
	public List<Author> getAuthors(Map<String, String> params) {
		return authorDao.getAuthors(params);
	}
	
	private void validateRequiredFields(Author author) {
		if (author.getName() == null || author.getName().isEmpty() 
				|| author.getLastname() == null || author.getLastname().isEmpty()) {
			throw new AuthorValidationException();
		}
	}

}
