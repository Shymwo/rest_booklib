package put.poznan.rest.booklib.model;

public abstract class BaseModel {
	
	public abstract Integer getId();

	public abstract void setId(Integer id);
	
	public abstract String getEtag();

	public abstract void setEtag(String etag);
	
}
