package repositorios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;


import compras.Compra;

import etiqueta.Etiqueta;
import item.Item;
import usuario.Usuario;

public class RepositorioEgresos implements WithGlobalEntityManager {
	
	public static RepositorioEgresos instancia = new RepositorioEgresos();
	
	public Compra buscar(long id) {
	    return (Compra) entityManager().find(Compra.class, id);
	}
	
	public void agregarARevisores(long idUsuario, Compra nuevaCompra) {
		Usuario usuarios = repositorios.RepositorioUsuarios.instancia.buscar(idUsuario);
		nuevaCompra.agregarRevisor(usuarios);
	}
	
	public void agregarAEtiquetas(long idEtiqueta, Compra nuevaCompra) {
		Etiqueta etiqueta = repositorios.RepositorioEtiquetas.instancia.buscar(idEtiqueta);
		nuevaCompra.agregarEtiqueta(etiqueta);
	}
	public void agregarAItems(Item item, Compra nuevaCompra) {
		nuevaCompra.agregarItem(item);
	}
	
	public List<?> listar(String tabla, Class<?> clase) {
		return entityManager()
				.createQuery("from " + tabla, clase)
				.getResultList();
	}
	
	public List<Compra> listarCompras(){
		return entityManager()
				.createQuery("from Compra", Compra.class)
				.getResultList();
	}
	
	
	public List<Compra> listarSegunProveedor(long idProveedor) {
		return entityManager()
				.createQuery("from " + "Compra" + " where proveedor_id like :id", Compra.class)
				.setParameter("id", idProveedor)
				.getResultList();
	}

	
}