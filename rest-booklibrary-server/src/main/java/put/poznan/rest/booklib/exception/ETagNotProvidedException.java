package put.poznan.rest.booklib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
public class ETagNotProvidedException extends RuntimeException {

	private static final long serialVersionUID = -8643945474632162343L;
	
	public ETagNotProvidedException(String eTag) {
		super(eTag);
	}

}
