package put.poznan.rest.booklib.dao;

import put.poznan.rest.booklib.model.Token;

public interface TokenDao {
	
	public void addToken(Token token);
	public Token getToken(String token);
	public void removeToken(Token token);
	
}
