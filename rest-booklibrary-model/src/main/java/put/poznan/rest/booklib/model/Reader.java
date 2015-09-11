package put.poznan.rest.booklib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="czytelnicy")
public class Reader implements Serializable {
	
	private static final long serialVersionUID = -3021973812347819044L;

	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="etag")
	@JsonIgnore
	private String etag;
	
	@Column(name="imie")
	private String name;
	
	@Column(name="nazwisko")
	private String lastname;
	
	@Column(name="haslo")
	private String password;
	
	@Column(name="data_urodzenia")
	private Date birthDate;
	
	@Column(name="miejsce_zamieszkania")
	private String address;
	
	@Column(name="telefon")
	private String telephoneNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}
	
}
