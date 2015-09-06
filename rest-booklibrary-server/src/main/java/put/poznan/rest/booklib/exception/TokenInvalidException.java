package put.poznan.rest.booklib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
reason="Given token is invalid or it was already used. Try generating a new one.")
public class TokenInvalidException extends RuntimeException {

	private static final long serialVersionUID = -8643945474632162343L;
	
	public TokenInvalidException() {
		super();
	}

}
