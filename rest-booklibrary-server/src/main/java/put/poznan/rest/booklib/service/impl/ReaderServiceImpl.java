package put.poznan.rest.booklib.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import put.poznan.rest.booklib.dao.ReaderDao;
import put.poznan.rest.booklib.exception.ContentNotFoundException;
import put.poznan.rest.booklib.exception.ETagInvalidException;
import put.poznan.rest.booklib.exception.ETagNotProvidedException;
import put.poznan.rest.booklib.exception.ReaderValidationException;
import put.poznan.rest.booklib.exception.TokenInvalidException;
import put.poznan.rest.booklib.model.Reader;
import put.poznan.rest.booklib.service.ReaderService;
import put.poznan.rest.booklib.util.TokenUtil;

@Service
public class ReaderServiceImpl implements ReaderService {
	
	@Autowired
	private ReaderDao readerDao;
	
	@Autowired
	private TokenUtil tokenUtil;

	@Transactional
	public void addReader(Reader reader, String token) {
		validateRequiredFields(reader);
		if (tokenUtil.usePostToken(token)) {
			reader.setId(null);
			reader.setEtag(tokenUtil.getNewETag());
			readerDao.addReader(reader);
		} else {
			throw new TokenInvalidException();
		}
	}

	@Transactional
	public void updateReader(Reader reader) {
		validateRequiredFields(reader);
		Reader temp = getReader(reader.getId());
		if (reader.getEtag() == null) {
			throw new ETagNotProvidedException(temp.getEtag());
		}
		if (!reader.getEtag().equals(temp.getEtag())) {
			throw new ETagInvalidException(temp.getEtag());
		}
		reader.setEtag(tokenUtil.getNewETag());
		readerDao.updateReader(reader);
	}

	@Transactional
	public void removeReader(Integer id, String eTag) {
		Reader reader = getReader(id);
		if (eTag == null) {
			throw new ETagNotProvidedException(reader.getEtag());
		}
		if (!eTag.equals(reader.getEtag())) {
			throw new ETagInvalidException(reader.getEtag());
		}
		readerDao.removeReader(reader);
	}

	@Transactional
	public Reader getReader(Integer id) {
		Reader reader = readerDao.getReader(id);
		if (reader == null) {
			throw new ContentNotFoundException();
		}
		return reader;
	}

	@Transactional
	public List<Reader> getReaders(Map<String, String> params) {
		return readerDao.getReaders(params);
	}
	
	private void validateRequiredFields(Reader reader) {
		if (reader.getName() == null || reader.getName().isEmpty() 
				|| reader.getLastname() == null || reader.getLastname().isEmpty()) {
			throw new ReaderValidationException();
		}
	}

}
