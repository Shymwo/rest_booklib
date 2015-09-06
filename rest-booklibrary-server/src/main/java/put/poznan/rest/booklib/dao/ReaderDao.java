package put.poznan.rest.booklib.dao;

import java.util.List;

import put.poznan.rest.booklib.model.Reader;

public interface ReaderDao {
	
	public void addReader(Reader reader);
	public void updateReader(Reader reader);
	public void removeReader(Integer id);
	public Reader getReader(Integer id);
	public List<Reader> getAllReaders();

}
