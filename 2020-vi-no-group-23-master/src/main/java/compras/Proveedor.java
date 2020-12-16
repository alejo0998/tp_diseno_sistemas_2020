package compras;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import atributos.DireccionPostal;

@Entity @Table(name="proveedores")
public class Proveedor{

	@Id
	private Long id;
	private String nombre;
	@OneToOne @JoinColumn(name = "direccion_postal")
	private DireccionPostal direccionPostal;
	
	public Proveedor(String nombreVendedor, long id, DireccionPostal direccionPostalVendedor){
		this.nombre = nombreVendedor;
		this.id = id;
		this.direccionPostal = direccionPostalVendedor;
	}
	
	public Proveedor() {
		super();
	}

	public Long getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
}
