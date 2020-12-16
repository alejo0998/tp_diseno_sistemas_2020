package reglasNegocio;

import javax.persistence.Entity;

import entidades.Entidad;

@Entity
public class NoPuedeAgregarEntidadBase extends ReglaNegocio {
	
	public NoPuedeAgregarEntidadBase() {
		super(TipoReglaNegocio.NO_AGREGAR_ENTIDAD_BASE);
	}
	
	public boolean valor(Entidad entidad) {
		return true;
	}
	
}