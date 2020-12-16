package atributos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name="monedas")
public class Moneda {

	@Id @SerializedName("id")
	// No es autogenerado porque lo recibo de la API. Ej: "USD".
	private String id;
    @SerializedName("description")
    private String descripcion;
    @SerializedName("symbol")
    private String simbolo;
    @SerializedName("decimal_places")
    private int decimales;

    public Moneda(String descripcion, String id, String simbolo, int decimales) {
        this.descripcion = descripcion;
        this.id = id;
        this.simbolo = simbolo;
        this.decimales = decimales;
    }
    
    public Moneda() {
    	super();
    }

	public String getDescripcion() {
		return descripcion;
	}
	
	public String getId() {
		return id;
	}
	
	
	public String getSimbolo() {
		return simbolo;
	}
	
	public int getDecimales() {
		return decimales;
	}

}