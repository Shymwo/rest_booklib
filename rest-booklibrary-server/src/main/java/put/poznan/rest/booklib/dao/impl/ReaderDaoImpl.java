package put.poznan.rest.booklib.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import put.poznan.rest.booklib.dao.ReaderDao;
import put.poznan.rest.booklib.model.Reader;

import com.mysql.jdbc.StringUtils;

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
		Session session = sessionFactory.getCurrentSession();
		Reader oldReader = (Reader) session.merge(reader);
		sessionFactory.getCurrentSession().update(oldReader);
	}

	@Override
	public void removeReader(Reader reader) {
		sessionFactory.getCurrentSession().delete(reader);
	}

	@Override
	public Reader getReader(Integer id) {
		sessionFactory.getCurrentSession().flush();
		return (Reader) sessionFactory.getCurrentSession().get(Reader.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reader> getReaders(Map<String, String> params) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Reader.class);
		String nameLike = params.get("nameLike");
		if (!StringUtils.isNullOrEmpty(nameLike)) {
			cr.add(Restrictions.ilike("name", "%"+nameLike+"%"));
		}
		String lastnameLike = params.get("lastnameLike");
		if (!StringUtils.isNullOrEmpty(lastnameLike)) {
			cr.add(Restrictions.ilike("lastname", "%"+lastnameLike+"%"));
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

}
