package put.poznan.rest.booklib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8686564352498346319L;
	
	public ContentNotFoundException() {
		super();
	}

}
