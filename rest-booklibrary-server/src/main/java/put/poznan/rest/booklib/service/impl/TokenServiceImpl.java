package put.poznan.rest.booklib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import put.poznan.rest.booklib.service.TokenService;
import put.poznan.rest.booklib.util.TokenUtil;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private TokenUtil tokenUtil;

	@Transactional
	public String createNewToken() {
		return tokenUtil.getNewPostToken();
	}

}
