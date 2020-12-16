package atributos;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="direccion_postal")
public class DireccionPostal {
	
	@Id @GeneratedValue
	private Long id;
	@Embedded
	private Pais pais;
	@Embedded
	private Provincia provincia;
	@Embedded
	private Ciudad ciudad;
	@Embedded
	private Direccion direccion;
	
	public DireccionPostal(Pais pais, Provincia provincia, Ciudad ciudad, Direccion direccion) {
		this.pais = pais;
		this.provincia = provincia;
		this.ciudad = ciudad;
		this.direccion = direccion;
	}
	
	public Ciudad obtenerCiudad() {
		return ciudad;
	}
	public Provincia obtenerProvincia() {
		return provincia;
	}
	public Pais obtenerPais() {
		return pais;
	}
	public Direccion obtenerDireccion() {
		return direccion;
	}
}