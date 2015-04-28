package es.ucm.abd.practica2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Contiene")
public class Contiene {
	
	@GeneratedValue
	@Id
	private Integer id;
	private Integer x;
	private Integer y;
	private Orientation orientacion;
	@ManyToOne
	private Crucigrama crucigrama;
	@OneToOne
	private Palabra palabra;
	
	protected Contiene(){}
	
	public Contiene(Integer x, Integer y, Orientation orientacion,
			Crucigrama c, Palabra p) {
		this.id = null;
		this.x = x;
		this.y = y;
		this.orientacion = orientacion;
		this.crucigrama = c;
		this.palabra = p;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Orientation getOrientacion() {
		return orientacion;
	}
	public void setOrientacion(Orientation orientacion) {
		this.orientacion = orientacion;
	}
	public Crucigrama getCrucigrama() {
		return crucigrama;
	}
	public void setCrucigrama(Crucigrama crucigrama) {
		this.crucigrama = crucigrama;
	}
	public Palabra getPalabra() {
		return palabra;
	}
	public void setPalabra(Palabra palabra) {
		this.palabra = palabra;
	}

	
}
