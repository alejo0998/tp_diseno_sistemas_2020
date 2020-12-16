package atributos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.gson.annotations.SerializedName;

@Embeddable
public class Provincia {
	
	@Column(name="provincia_id")
	@SerializedName("id")
	private String id;
	@Column(name="provincia")
	@SerializedName("name")
	private String nombre;

	public Provincia(String nombreProvincia, String idProvincia) {
		nombre = nombreProvincia;
		id = idProvincia;
	}
	public String obtenerNombre() {
		return nombre;
	}
	public String obtenerId() {
		return id;
	}
}