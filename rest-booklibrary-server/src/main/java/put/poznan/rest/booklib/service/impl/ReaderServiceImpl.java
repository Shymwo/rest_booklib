package put.poznan.rest.booklib.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import put.poznan.rest.booklib.dao.ReaderDao;
import put.poznan.rest.booklib.model.Reader;
import put.poznan.rest.booklib.service.ReaderService;

@Service
public class ReaderServiceImpl implements ReaderService {
	
	@Autowired
	private ReaderDao readerDao;

	@Transactional
	public void addReader(Reader reader) {
		readerDao.addReader(reader);
	}

	@Transactional
	public void updateReader(Reader reader) {
		readerDao.updateReader(reader);
	}

	@Transactional
	public void removeReader(Integer id) {
		readerDao.removeReader(id);
	}

	@Transactional
	public Reader getReader(Integer id) {
		return readerDao.getReader(id);
	}

	@Transactional
	public List<Reader> getAllReaders() {
		return readerDao.getAllReaders();
	}

}
