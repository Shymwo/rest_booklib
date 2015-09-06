package put.poznan.rest.booklib.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import put.poznan.rest.booklib.dao.AuthorDao;
import put.poznan.rest.booklib.model.Author;

@Repository
public class AuthorDaoImpl implements AuthorDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addAuthor(Author author) {
		sessionFactory.getCurrentSession().save(author);
	}

	@Override
	public void updateAuthor(Author author) {
		sessionFactory.getCurrentSession().update(author);
	}

	@Override
	public void removeAuthor(Integer id) {
		Author author = (Author) sessionFactory.getCurrentSession().load(
				Author.class, id);
		if (author != null) {
			sessionFactory.getCurrentSession().delete(author);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Author getAuthor(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Author where id = :authorId");
		query.setParameter("authorId", id);
		List<Author> authors = query.list();
		if (authors != null && !authors.isEmpty()) {
			return authors.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Author> getAllAuthors() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Author").list();
	}

}
