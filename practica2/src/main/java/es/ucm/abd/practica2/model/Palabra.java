package es.ucm.abd.practica2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Palabra")
public class Palabra {

	@GeneratedValue
	@Id
	private Integer id;
	@OneToMany
	private String palabra;
	private String enunciado;
	private byte[] imagen;
	private String[] etiquetas;

	public Palabra(Integer id, String palabra, String enunciado, byte[] imagen,
			String[] etiquetas) {
		this.id = id;
		this.palabra = palabra;
		this.enunciado = enunciado;
		this.imagen = imagen;
		this.etiquetas = etiquetas;
	}
	
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String[] getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String[] etiquetas) {
		this.etiquetas = etiquetas;
	}

}
