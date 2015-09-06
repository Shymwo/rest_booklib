package put.poznan.rest.booklib.util;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import put.poznan.rest.booklib.dao.TokenDao;
import put.poznan.rest.booklib.model.Token;

@Component
public class TokenUtil {

	@Autowired
	private TokenDao tokenDao;
	
	private SecureRandom random = new SecureRandom();
	
	private String generateToken() {
		BigInteger randomNumber = new BigInteger(50, random);
		String tokenStr = randomNumber.toString(32);
		return tokenStr;		
	}
	
	public String getNewETag() {
		String tokenStr = generateToken();
		return "\""+tokenStr+"\"";
	}
	  
	public String getNewPostToken() {
		String tokenStr = generateToken();
		Token token = new Token();
		token.setToken(tokenStr);
		tokenDao.addToken(token);
		return tokenStr;
	}
	
	public boolean usePostToken(String tokenStr) {
		Token token = tokenDao.getToken(tokenStr);
		if (token != null) {
			tokenDao.removeToken(token);
			return true;
		}
		return false;
	}
	
}
