package put.poznan.rest.booklib.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;

import put.poznan.rest.booklib.dao.BookDao;
import put.poznan.rest.booklib.model.Book;

@Repository
public class BookDaoImpl implements BookDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addBook(Book book) {
		sessionFactory.getCurrentSession().save(book);
	}

	@Override
	public void updateBook(Book book) {
		Session session = sessionFactory.getCurrentSession();
		Book oldBook = (Book) session.merge(book);
		sessionFactory.getCurrentSession().update(oldBook);
	}

	@Override
	public void removeBook(Book book) {
		sessionFactory.getCurrentSession().delete(book);
	}

	@Override
	public Book getBook(Integer id) {
		sessionFactory.getCurrentSession().flush();
		return (Book) sessionFactory.getCurrentSession().get(Book.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooks(Map<String, String> params) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Book.class, "book");
		cr.createAlias("book.author", "author");
		cr.createAlias("book.reader", "reader", Criteria.LEFT_JOIN);
		String titleLike = params.get("titleLike");
		if (!StringUtils.isNullOrEmpty(titleLike)) {
			cr.add(Restrictions.ilike("book.title", "%"+titleLike+"%"));
		}
		String authorId = params.get("authorId");
		if (!StringUtils.isNullOrEmpty(authorId)) {
			cr.add(Restrictions.eq("author.id", Integer.parseInt(authorId)));
		}
		String authorNameLike = params.get("authorNameLike");
		if (!StringUtils.isNullOrEmpty(authorNameLike)) {
			cr.add(Restrictions.ilike("author.name", "%"+authorNameLike+"%"));
		}
		String authorLastnameLike = params.get("authorLastnameLike");
		if (!StringUtils.isNullOrEmpty(authorLastnameLike)) {
			cr.add(Restrictions.ilike("author.lastname", "%"+authorLastnameLike+"%"));
		}
		String genreLike = params.get("genreLike");
		if (!StringUtils.isNullOrEmpty(genreLike)) {
			cr.add(Restrictions.ilike("book.genre", "%"+genreLike+"%"));
		}
		String readerId = params.get("readerId");
		if (!StringUtils.isNullOrEmpty(readerId)) {
			cr.add(Restrictions.eq("reader.id", Integer.parseInt(readerId)));
		}
		String pageStr = params.get("page");
		String pageSizeStr = params.get("pageSize");
		if (!StringUtils.isNullOrEmpty(pageStr) || !StringUtils.isNullOrEmpty(pageSizeStr)) {
			Integer page;
			Integer pageSize;
			if (!StringUtils.isNullOrEmpty(pageStr)) {
				page = Integer.parseInt(pageStr);
			} else {
				page = 0;
			}
			if (!StringUtils.isNullOrEmpty(pageSizeStr)) {
				pageSize = Integer.parseInt(pageSizeStr);
			} else {
				pageSize = 10;
			}
			cr.setFirstResult(page*pageSize);
			cr.setMaxResults(pageSize);
		}
		return cr.list();
	}
	
	@Override
	public void returnAllBooks(Integer readerId) {
		Query query = sessionFactory.getCurrentSession().
				createQuery("update Book set czytelnik_id = null where czytelnik_id = :readerId");
		query.setParameter("readerId", readerId);
		query.executeUpdate();
	}

}
