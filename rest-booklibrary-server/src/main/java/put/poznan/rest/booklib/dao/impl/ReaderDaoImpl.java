package put.poznan.rest.booklib.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import put.poznan.rest.booklib.dao.ReaderDao;
import put.poznan.rest.booklib.model.Reader;

@Repository
public class ReaderDaoImpl implements ReaderDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addReader(Reader reader) {
		sessionFactory.getCurrentSession().save(reader);
	}

	@Override
	public void updateReader(Reader reader) {
		sessionFactory.getCurrentSession().update(reader);
	}

	@Override
	public void removeReader(Integer id) {
		Reader reader = (Reader) sessionFactory.getCurrentSession().load(
				Reader.class, id);
		if (reader != null) {
			sessionFactory.getCurrentSession().delete(reader);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Reader getReader(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Reader where id = :readerId");
		query.setParameter("readerId", id);
		List<Reader> readers = query.list();
		if (readers != null && !readers.isEmpty()) {
			return readers.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reader> getAllReaders() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Reader").list();
	}

}
