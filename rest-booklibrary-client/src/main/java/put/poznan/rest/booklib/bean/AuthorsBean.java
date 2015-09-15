package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import put.poznan.rest.booklib.model.Author;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class AuthorsBean extends CommonBean<Author> implements Serializable {
	
	private static final long serialVersionUID = 1105095805699585811L;

	private String nameLike;
	
	private String lastnameLike;
		
	@Override
	public void loadNewRecord() {
		setSelectedRecord(new Author());
	}

	@Override
	public String getUrl() {
		return PropertyUtil.getProperty("rest.uri")+"/authors";
	}

	@Override
	public Map<String, String> getVarsForLoadRecords() {
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("nameLike", nameLike);
		urlVariables.put("lastnameLike", lastnameLike);
		return urlVariables;
	}

	@Override
	public String getUrlForLoadRecords() {
		return PropertyUtil.getProperty("rest.uri")
				+"/authors?&nameLike={nameLike}&lastnameLike={lastnameLike}";
	}

	@Override
	public Class<Author> getRecordClass() {
		return Author.class;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getLastnameLike() {
		return lastnameLike;
	}

	public void setLastnameLike(String lastnameLike) {
		this.lastnameLike = lastnameLike;
	}

}