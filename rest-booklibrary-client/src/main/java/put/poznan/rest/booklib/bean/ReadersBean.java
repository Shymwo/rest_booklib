package put.poznan.rest.booklib.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import put.poznan.rest.booklib.model.Reader;
import put.poznan.rest.booklib.util.PropertyUtil;
 
public class ReadersBean extends CommonBean<Reader> implements Serializable {
    
	private static final long serialVersionUID = -4642774537753298469L;
	
	private String nameLike;
	
	private String lastnameLike;
	
	@Override
	public void loadNewRecord() {
		setSelectedRecord(new Reader());
	}

	@Override
	public String getUrl() {
		return PropertyUtil.getProperty("rest.uri")+"/readers";
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
				+"/readers?&nameLike={nameLike}&lastnameLike={lastnameLike}";
	}

	@Override
	public Class<Reader> getRecordClass() {
		return Reader.class;
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