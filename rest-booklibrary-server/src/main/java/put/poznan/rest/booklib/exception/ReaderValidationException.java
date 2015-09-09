package put.poznan.rest.booklib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
reason="Required fields are not provided in the request. "
		+ "Fill in name, lastname and try again.")
public class ReaderValidationException extends RuntimeException {
	private static final long serialVersionUID = -8643945474632162343L;
	
	public ReaderValidationException() { 
		super(); 
	}
}
