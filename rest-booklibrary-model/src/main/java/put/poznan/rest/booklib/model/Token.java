package put.poznan.rest.booklib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tokeny")
public class Token implements Serializable {
	
	private static final long serialVersionUID = -4034899849660868808L;

	@Id
	@Column(name="token")
	private String token;
	
	@Column(name="data_utworzenia")
	private Date timestamp;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
