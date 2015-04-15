package es.ucm.abd.practica2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Crucigrama")
public class Crucigrama {

	@GeneratedValue
	@Id
	private Integer id;
	private String titulo;
	private Date fechaCreacion;
	@OneToMany(mappedBy="crucigrama" ,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<Contiene> palabras;
	
	public Crucigrama(Integer id, String titulo, Date fechaCreacion,
			ArrayList<Contiene> palabras) {
		this.id = id;
		this.titulo = titulo;
		this.fechaCreacion = fechaCreacion;
		this.palabras = palabras;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public List<Contiene> getPalabras() {
		return palabras;
	}
	public void setPalabras(ArrayList<Contiene> palabras) {
		this.palabras = palabras;
	}
	
	public void addWord(Crucigrama c,Palabra word, int row, int column, Orientation orientation){
		this.palabras.add(new Contiene(0,row,column,orientation,c,word));
	}
}
