package reglasNegocio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import entidades.Entidad;

@Entity @Table(name="reglas_de_negocio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ReglaNegocio {
	
	@Id @GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING) @Column(name="tipo")
	protected TipoReglaNegocio tipoReglaNegocio;
	
	public ReglaNegocio(TipoReglaNegocio tipoReglaNegocio) {
		this.tipoReglaNegocio = tipoReglaNegocio;
	}
	
    public TipoReglaNegocio tipoReglaNegocio() {
        return tipoReglaNegocio;
    }
	
	public boolean valor() {
		return true;
	}
	
	public boolean valor(Entidad entidad){
		return true;
	}
}