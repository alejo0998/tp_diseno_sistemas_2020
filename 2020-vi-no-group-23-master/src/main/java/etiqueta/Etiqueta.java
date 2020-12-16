package etiqueta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import entidades.Organizacion;

@Entity @Table(name="etiquetas")
public class Etiqueta {

	@Id @GeneratedValue
	private Long id;
	@SuppressWarnings("unused")
	private String nombre;
	//@SuppressWarnings("unused")
	//private Organizacion organizacion;
	
	public Etiqueta(String nombre) {
		this.nombre = nombre;
	}
	
	public Etiqueta() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
