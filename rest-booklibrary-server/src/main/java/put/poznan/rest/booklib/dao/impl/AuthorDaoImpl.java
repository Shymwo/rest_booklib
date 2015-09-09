package put.poznan.rest.booklib.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import put.poznan.rest.booklib.dao.AuthorDao;
import put.poznan.rest.booklib.model.Author;

import com.mysql.jdbc.StringUtils;

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
	public void removeAuthor(Author author) {
		sessionFactory.getCurrentSession().delete(author);
	}

	@Override
	public Author getAuthor(Integer id) {
		return (Author) sessionFactory.getCurrentSession().get(Author.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Author> getAuthors(Map<String, String> params) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Author.class);
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
