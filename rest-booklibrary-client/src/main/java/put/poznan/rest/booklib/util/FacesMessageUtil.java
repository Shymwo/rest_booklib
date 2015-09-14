package put.poznan.rest.booklib.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public final class FacesMessageUtil {
	
	private FacesMessageUtil() {}
	
	public static void showSuccessInfo() {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation completed", null));		
	}
	
	public static void showInvalidStatusWarn(HttpStatus status) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning! Invalid response status: "
						+status.value()+" "+status.getReasonPhrase(), null));		
	}
	
	public static void showError(Exception e) {
		String errorMsg = e.getMessage();
		if (e instanceof HttpClientErrorException) {
			HttpClientErrorException e1 = (HttpClientErrorException) e;
			if (e1.getResponseBodyAsString() != null && !e1.getResponseBodyAsString().isEmpty()) {
				errorMsg += ": "+e1.getResponseBodyAsString();
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! "+errorMsg, null));		
	}

}
