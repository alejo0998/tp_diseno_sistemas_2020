package reglasNegocio;

import javax.persistence.Entity;

import entidades.Entidad;

@Entity
public class NoPuedeSerParteDeEntidadJuridica extends ReglaNegocio {
	
	public NoPuedeSerParteDeEntidadJuridica() {
		super(TipoReglaNegocio.NO_SER_DE_ENTIDAD_JURIDICA);
	}
	
	public boolean valor(Entidad entidad) {
		return false;
	}
	
}
