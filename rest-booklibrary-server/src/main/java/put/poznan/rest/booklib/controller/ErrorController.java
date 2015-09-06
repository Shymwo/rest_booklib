package put.poznan.rest.booklib.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error") 
public class ErrorController {

	@RequestMapping
	public @ResponseBody String customError(HttpServletRequest request) {
		String message = (String) request.getAttribute("javax.servlet.error.message");
		return message;
	}
}
