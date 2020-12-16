package reglasNegocio;

import javax.persistence.Column;
import javax.persistence.Entity;

import entidades.Entidad;

@Entity
public class AceptarNuevosEgresos extends ReglaNegocio {
	
	@Column(name="monto_limite")
	private int montoLimite;
	
	public AceptarNuevosEgresos(int cantidad) {
		super(TipoReglaNegocio.ACEPTAR_EGRESOS);
		this.montoLimite = cantidad;
	}
	
	public boolean valor(Entidad entidad) {
		return montoLimite > entidad.cantidadEgresos();
	}
	
}
