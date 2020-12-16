package atributos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.gson.annotations.SerializedName;

@Embeddable
public class Ciudad {
	
	@Column(name="ciudad_id")
	@SerializedName("id")
	private String id;
	@Column(name="ciudad")
	@SerializedName("name")
	private String nombre;
	
	public Ciudad(String nombreCiudad, String idCiudad) {
		this.nombre = nombreCiudad;
		this.id = idCiudad;
	}
	public String obtenerNombre() {
		return nombre;
	}
	public String obtenerId() {
		return id;
	}
}