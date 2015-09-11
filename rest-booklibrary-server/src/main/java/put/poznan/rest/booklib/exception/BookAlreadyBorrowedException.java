package put.poznan.rest.booklib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_MODIFIED, 
reason="This book was already borrowed.")
public class BookAlreadyBorrowedException extends RuntimeException {
	private static final long serialVersionUID = -8643945474632162343L;
	
	public BookAlreadyBorrowedException() { 
		super(); 
	}
}
