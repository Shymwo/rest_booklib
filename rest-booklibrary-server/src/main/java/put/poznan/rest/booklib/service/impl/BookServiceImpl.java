package put.poznan.rest.booklib.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import put.poznan.rest.booklib.dao.BookDao;
import put.poznan.rest.booklib.exception.BookAlreadyBorrowedException;
import put.poznan.rest.booklib.exception.BookValidationException;
import put.poznan.rest.booklib.exception.ContentNotFoundException;
import put.poznan.rest.booklib.exception.ETagInvalidException;
import put.poznan.rest.booklib.exception.ETagNotProvidedException;
import put.poznan.rest.booklib.exception.TokenInvalidException;
import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.model.Book;
import put.poznan.rest.booklib.model.Reader;
import put.poznan.rest.booklib.service.BookService;
import put.poznan.rest.booklib.util.TokenUtil;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private TokenUtil tokenUtil;

	@Transactional
	public void addBook(Book book, String token) {
		validateRequiredFields(book);
		if (tokenUtil.usePostToken(token)) {
			book.setId(null);
			book.setEtag(tokenUtil.getNewETag());
			bookDao.addBook(book);
		} else {
			throw new TokenInvalidException();
		}
	}
	
	@Transactional
	public void addBook(Book book, String token, Integer authorId) {
		if (authorId != null) {
			book.setAuthor(new Author());
			book.getAuthor().setId(authorId);
		}
		addBook(book, token);
	}

	@Transactional
	public void updateBook(Book book) {
		Book temp = getBook(book.getId());
		updateBook(book, temp.getEtag());
	}
	
	@Transactional
	public void updateBook(Book book, Integer authorId) {
		Book temp = getBook(book.getId(), authorId, null);
		updateBook(book, temp.getEtag());
	}
	
	private void updateBook(Book book, String eTag) {
		validateRequiredFields(book);
		if (book.getEtag() == null) {
			throw new ETagNotProvidedException(eTag);
		}
		if (!book.getEtag().equals(eTag)) {
			throw new ETagInvalidException(eTag);
		}
		book.setEtag(tokenUtil.getNewETag());
		bookDao.updateBook(book);		
	}

	@Transactional
	public void removeBook(Integer id, String eTag) {
		Book book = getBook(id);
		removeBook(book, eTag);
	}
	
	@Transactional
	public void removeBook(Integer id, String eTag, Integer authorId) {
		Book book = getBook(id, authorId, null);
		removeBook(book, eTag);
	}
	
	private void removeBook(Book book, String eTag) {
		if (eTag == null) {
			throw new ETagNotProvidedException(book.getEtag());
		}
		if (!eTag.equals(book.getEtag())) {
			throw new ETagInvalidException(book.getEtag());
		}
		bookDao.removeBook(book);		
	}
	

	@Transactional
	public Book getBook(Integer id) {
		Book book = bookDao.getBook(id);
		if (book == null) {
			throw new ContentNotFoundException();
		}
		return book;
	}
	
	@Transactional
	public Book getBook(Integer id, Integer authorId, Integer readerId) {
		Book book = getBook(id);
		if (authorId != null && 
				(book.getAuthor() == null || !authorId.equals(book.getAuthor().getId()))
			) {
			throw new ContentNotFoundException();
		}
		if (readerId != null && 
				(book.getReader() == null || !readerId.equals(book.getReader().getId()))
			) {
			throw new ContentNotFoundException();
		}
		return book;
	}

	@Transactional
	public List<Book> getBooks(Map<String, String> params) {
		return bookDao.getBooks(params);
	}
	
	private void validateRequiredFields(Book book) {
		if (book.getTitle() == null || book.getTitle().isEmpty() 
				|| book.getGenre() == null || book.getGenre().isEmpty()
				|| book.getAuthor() == null || book.getAuthor().getId() == null) {
			throw new BookValidationException();
		}
	}

	@Transactional
	public void borrowBookByReader(Integer id, Integer readerId, String eTag) {
		Book book = getBook(id);
		if (book.getReader() != null && book.getReader().getId() != null) {
			throw new BookAlreadyBorrowedException();
		}
		if (book.getEtag() == null) {
			throw new ETagNotProvidedException(eTag);
		}
		if (!book.getEtag().equals(eTag)) {
			throw new ETagInvalidException(eTag);
		}
		book.setEtag(tokenUtil.getNewETag());
		book.setReader(new Reader());
		book.getReader().setId(readerId);
		bookDao.updateBook(book);
	}

	@Transactional
	public void returnBookByReader(Integer id, Integer readerId, String eTag) {
		Book book = getBook(id, null, readerId);
		if (book.getEtag() == null) {
			throw new ETagNotProvidedException(eTag);
		}
		if (!book.getEtag().equals(eTag)) {
			throw new ETagInvalidException(eTag);
		}
		book.setEtag(tokenUtil.getNewETag());
		book.setReader(null);
		bookDao.updateBook(book);
	}

	@Transactional
	public void returnAllBooks(Integer readerId) {
		bookDao.returnAllBooks(readerId);
	}

}
