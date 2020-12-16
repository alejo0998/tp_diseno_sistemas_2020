package entidades;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import categoria.Categoria;
import reglasNegocio.TipoReglaNegocio;

@MappedSuperclass
public abstract class Entidad {
	
	private int cantidadEgresos = 0;
	@ManyToOne(fetch = FetchType.LAZY)
	public Categoria categoria = null;

	public int cantidadEgresos() {
		return cantidadEgresos;
	}
	
	public void sumarEgreso() {
		cantidadEgresos += 1;
	}
	
	public void agregarCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void eliminarCategoria() {
		this.categoria = null;
	}
	
	public boolean puedeAceptarNuevoEgreso() {
		if (categoria != null) {
			return categoria.cumpleConRegla(TipoReglaNegocio.ACEPTAR_EGRESOS, this);
		}
		return true;
	}
	
	public int getCantidadEgresos() {
		return cantidadEgresos;
	}
}
