package atributos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.gson.annotations.SerializedName;

@Embeddable
public class Pais {
	
	@Column(name="pais_id")
	@SerializedName("id")
	private String id;
	@Column(name="pais")
	@SerializedName("name")
	private String nombre;
	
	public Pais(String nombrePais, String idPais) {
		nombre = nombrePais;
		id = idPais;
	}
	
	public String obtenerNombre() {
		return nombre;
	}
}