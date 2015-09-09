package put.poznan.rest.booklib.dao;

import java.util.List;
import java.util.Map;

import put.poznan.rest.booklib.model.Reader;

public interface ReaderDao {
	
	public void addReader(Reader reader);
	public void updateReader(Reader reader);
	public void removeReader(Reader reader);
	public Reader getReader(Integer id);
	public List<Reader> getReaders(Map<String, String> params);

}
