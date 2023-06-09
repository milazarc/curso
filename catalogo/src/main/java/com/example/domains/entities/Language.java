package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;


/**
 * The persistent class for the language database table.
 * 
 */
@Entity
@Table(name="language")
@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")
public class Language extends EntityBase<Language> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="language_id", unique=true, nullable=false)
	private int languageId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;

	@NotBlank
	@Column(nullable=false, length=20)
	private String name;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="language")
	private List<Film> films = new ArrayList<Film>();

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="languageVO")
	private List<Film> filmsVO = new ArrayList<Film>();

	public Language() {
	}

	public Language(int languageId) {
		super();
		this.languageId = languageId;
	}

	public Language(int languageId, String name) {
		super();
		this.languageId = languageId;
		this.name = name;
	}

	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilms() {
		return this.films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}
	
	public void clearFilms() {
		films = new ArrayList<Film>() ;
	}
	
	public void clearFilmsVO() {
		filmsVO = new ArrayList<Film>() ;
	}

	public void addFilm(Film film) {
		getFilms().add(film);
	}

	public void removeFilm(Film film) {
		getFilms().remove(film);
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	public void addFilmVO(Film filmsVO) {
		getFilmsVO().add(filmsVO);
	}

	public void removeFilmVO(Film filmsVO) {
		getFilmsVO().remove(filmsVO);
	}
	
	public Language merge(Language target) {
		target.languageId = languageId;
		target.name = name;
	
		// Borra las peliculas que sobran
		target.getFilms().stream()
			.filter(item -> !getFilms().contains(item))
			.forEach(item -> target.removeFilm(item));
		// Añade las peliculas que faltan
		getFilms().stream()
			.filter(item -> !target.getFilms().contains(item))
			.forEach(item -> target.addFilm(item));
		// Borra las peliculasVO que sobran
		target.getFilmsVO().stream()
			.filter(item -> !getFilmsVO().contains(item))
			.forEach(item -> target.removeFilmVO(item));
		// Añade las peliculasVO que faltan
		getFilmsVO().stream()
			.filter(item -> !target.getFilmsVO().contains(item))
			.forEach(item -> target.addFilmVO(item));
		return target;
	}

	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", lastUpdate=" + lastUpdate + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(languageId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		return languageId == other.languageId;
	}
	
	

}