package put.poznan.rest.booklib.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="ksiazki")
public class Book {
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="etag")
	@JsonIgnore
	private String etag;

	@Column(name="tytul")
	private String title;
	
	@Column(name="gatunek")
	private String genre;
	
	@Column(name="jezyk")
	private String language;
	
	@Column(name="data_wydania")
	private Date releaseDate;
	
	@Column(name="miejsce_wydania")
	private String releasePlace;
	
	@Column(name="isbn")
	private String isbn;
	
	@ManyToOne
    @JoinColumn(name="autor_id")
    private Author author;

	@ManyToOne
	@JoinColumn(name="czytelnik_id")
	private Reader reader;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleasePlace() {
		return releasePlace;
	}

	public void setReleasePlace(String releasePlace) {
		this.releasePlace = releasePlace;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

}
