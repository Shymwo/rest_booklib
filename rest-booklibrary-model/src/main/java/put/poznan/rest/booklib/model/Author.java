package put.poznan.rest.booklib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import put.poznan.rest.booklib.model.helper.JsonDateDeserializer;
import put.poznan.rest.booklib.model.helper.JsonDateSerializer;

@Entity
@Table(name="autorzy")
public class Author extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 6018665185754365417L;

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
	
	@Column(name="data_urodzenia")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date birthDate;
	
	@Column(name="data_smierci")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date deathDate;
	
	@Column(name="narodowosc")
	private String nationality;
	
	@Column(name="gatunki")
	private String genres;

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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

}
