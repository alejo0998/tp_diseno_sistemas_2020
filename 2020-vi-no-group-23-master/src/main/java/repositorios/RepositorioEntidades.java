package repositorios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import categoria.Categoria;
import entidades.Entidad;
import entidades.EntidadBase;
import entidades.EntidadJuridica;
import entidades.Organizacion;

public class RepositorioEntidades implements WithGlobalEntityManager {
	
	public static RepositorioEntidades instancia = new RepositorioEntidades();
	
	public EntidadJuridica buscar(long id, Class<?> clase) {
	    return (EntidadJuridica) entityManager().find(clase, id);
	}
	
	public EntidadBase buscarEntidadBase(long id) {
	    return (EntidadBase) entityManager().find(EntidadBase.class, id); 
	}
	
	public Organizacion buscarOrganizacion(long id) {
		return (Organizacion) entityManager().find(Organizacion.class, id);
	}
	
	public void agregarACategoria(long idCategoria, Entidad nuevaEntidad) {
		Categoria categoria = repositorios.RepositorioCategorias.instancia.buscar(idCategoria);
		nuevaEntidad.agregarCategoria(categoria);
	}

	public void agregarACategoria(long idCategoria, String idEntidadJuridica, Class<?> clase) {
		Entidad entidad = buscar(Integer.parseInt(idEntidadJuridica), clase);
		agregarACategoria(idCategoria, entidad);
	}
	
	public void agregarAEntidadJuridica(long idEntidadBase, EntidadJuridica nuevaEntidad) {
		EntidadBase entidad = buscarEntidadBase(idEntidadBase);
		nuevaEntidad.agregarEntidadAsociada(entidad);
	}
	public void agregarAOrganizacion(long idOrganizacion, EntidadJuridica nuevaEntidad) {
		Organizacion organizacion = buscarOrganizacion(idOrganizacion);
		organizacion.agregarEntidadJuridica(nuevaEntidad);
	}
	
	public void asignarAEntidadBase(long idOrganizacion, EntidadBase nuevaEntidad) {
		Organizacion organizacion = buscarOrganizacion(idOrganizacion);
		nuevaEntidad.asignarOrganizacion(organizacion);
	}
	
	
	public List<?> listar(String tabla, Class<?> clase) {
		return entityManager()
				.createQuery("from " + tabla, clase)
				.getResultList();
	}
	
	public List<Organizacion> listarOrganizaciones(){
		return entityManager()
				.createQuery("from Organizacion", Organizacion.class)
				.getResultList();
	}
	
	public List<EntidadBase> listarEntidadesBase() {
		return entityManager()
				.createQuery("from EntidadBase", EntidadBase.class)
				.getResultList();
	}
	
	public List<EntidadJuridica> listarEntidadesJuridicas() {
		return entityManager()
				.createQuery("from EntidadJuridica", EntidadJuridica.class)
				.getResultList();
	}
	
	
	public List<?> listarSegunCategoria(long idCategoria, String tabla, Class<?> clase) {
		return entityManager()
				.createQuery("from " + tabla + " where categoria_id like :id", clase)
				.setParameter("id", idCategoria)
				.getResultList();
	}
	
}