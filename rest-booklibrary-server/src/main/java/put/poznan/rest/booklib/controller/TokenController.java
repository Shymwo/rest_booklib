package put.poznan.rest.booklib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import put.poznan.rest.booklib.service.TokenService;

@Controller
@RequestMapping("/tokens")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createNewToken() {
		String token = tokenService.createNewToken();
		return new ResponseEntity<String>(token, HttpStatus.CREATED);
	}

}
