package put.poznan.rest.booklib.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import put.poznan.rest.booklib.model.Reader;
import put.poznan.rest.booklib.service.ReaderService;

@Controller
@RequestMapping("/readers")
public class ReaderController {
	
	@Autowired
	private ReaderService readerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Reader> getReaders(@RequestParam Map<String, String> params) {
		return readerService.getReaders(params);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Reader> getReader(@PathVariable Integer id) {
		Reader reader = readerService.getReader(id);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(reader.getEtag());
		return new ResponseEntity<Reader>(reader, responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Reader> postReader(@RequestBody Reader reader, @RequestParam String token, 
			UriComponentsBuilder b) {
		readerService.addReader(reader, token);
		UriComponents location = b.path("/readers/{id}").buildAndExpand(reader.getId());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setETag(reader.getEtag());
	    responseHeaders.setLocation(location.toUri());
		return new ResponseEntity<Reader>(reader, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<Reader> putReader(@PathVariable Integer id, @RequestBody Reader reader,
			@RequestHeader(value="If-Match", required = false) String eTag) {
		HttpHeaders responseHeaders = new HttpHeaders();
		reader.setId(id);
		reader.setEtag(eTag);
		readerService.updateReader(reader);
	    responseHeaders.setETag(reader.getEtag());
		return new ResponseEntity<Reader>(reader, responseHeaders, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReader(@PathVariable Integer id, 
			@RequestHeader(value="If-Match", required = false) String eTag) {
		readerService.removeReader(id, eTag);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, 
			reason="Given page or pageSize parameters are not valid numbers")
	@ExceptionHandler(NumberFormatException.class)
	public void wrongNumberFormat() {};
	
}
