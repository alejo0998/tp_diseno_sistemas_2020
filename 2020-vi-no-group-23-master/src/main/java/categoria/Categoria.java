package categoria;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import entidades.Entidad;
import reglasNegocio.ReglaNegocio;
import reglasNegocio.TipoReglaNegocio;

@Entity
@Table(name="categorias")
public class Categoria {
	
	@Id @GeneratedValue
	private Long id;
	private String nombre;
	@ManyToMany @JoinTable(name="reglas_por_categorias")
	private Collection<ReglaNegocio> reglas;
	
	public Categoria(String nombre, Collection<ReglaNegocio> reglas) {
		this.nombre = nombre;
		this.reglas = reglas;
	}
	
	public Categoria() {
		super();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Long getId() {
		return id;
	}
	
	public void modificarNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Collection<ReglaNegocio> getReglas() {
		return reglas;
	}
	
	public void eliminarRegla(ReglaNegocio regla) {
		reglas.remove(regla);
	}
	
	public void agregarRegla(ReglaNegocio regla) {
		reglas.add(regla);
	}

	public boolean cumpleConRegla(TipoReglaNegocio tipoReglaNegocio, Entidad entidad) {
	    for (ReglaNegocio reglaNegocio : reglas) {
	        if(reglaNegocio.tipoReglaNegocio() == tipoReglaNegocio) {
	            return reglaNegocio.valor(entidad);
	        }
	    }
	    return true;
	}
}