package put.poznan.rest.booklib.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import put.poznan.rest.booklib.dao.TokenDao;
import put.poznan.rest.booklib.model.Token;

@Repository
public class TokenDaoImpl implements TokenDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addToken(Token token) {
		sessionFactory.getCurrentSession().save(token);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Token getToken(String token) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Token where token = :tokenStr");
		query.setParameter("tokenStr", token);
		List<Token> tokens = query.list();
		if (tokens != null && !tokens.isEmpty()) {
			return tokens.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void removeToken(Token token) {
		sessionFactory.getCurrentSession().delete(token);
	}
	

  
}
