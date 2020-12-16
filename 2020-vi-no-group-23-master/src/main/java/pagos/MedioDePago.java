package pagos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="medios_de_pago")
public class MedioDePago {

	@Id @GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(name="metodo")
	private MetodoPago metodoPago;
	// Número de tarjeta de credito, por ejemplo
	@SuppressWarnings("unused")
	private String identificacion;
	
	public MedioDePago(MetodoPago tipo, String identificacion){
		this.metodoPago = tipo;
		this.identificacion = identificacion;
	}
}
