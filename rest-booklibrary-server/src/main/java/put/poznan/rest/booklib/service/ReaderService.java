package put.poznan.rest.booklib.service;

import java.util.List;
import java.util.Map;

import put.poznan.rest.booklib.model.Reader;

public interface ReaderService {
	
	public void addReader(Reader reader, String token);
	public void updateReader(Reader reader);
	public void removeReader(Integer id, String eTag);
	public Reader getReader(Integer id);
	public List<Reader> getReaders(Map<String, String> params);
	
}
