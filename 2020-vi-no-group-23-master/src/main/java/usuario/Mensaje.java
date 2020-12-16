package usuario;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import compras.Proveedor;
import compras.EstadoValidacion;

@Entity @Table(name="mensajes")
public class Mensaje {
	
	@Id @GeneratedValue
	private Long id;
	@ManyToOne
	private Proveedor proveedor;
	@Enumerated(EnumType.STRING) @Column(name="estado_validacion")
	private EstadoValidacion estadoValidacion;
	@SuppressWarnings("unused")
	private LocalDate fecha;
	private String contenido;
	
	public Mensaje(Proveedor proveedor, EstadoValidacion estadoValidacion, LocalDate fecha, String contenido) {
		this.proveedor = proveedor;
		this.estadoValidacion = estadoValidacion;
		this.fecha = fecha;
		this.contenido = contenido;
	}

	public String getContenido() {
		return contenido;
	}
}
