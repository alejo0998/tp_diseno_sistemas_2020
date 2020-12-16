package repositorios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import compras.Proveedor;

public class RepositorioProveedores implements WithGlobalEntityManager {

	public static RepositorioProveedores instancia = new RepositorioProveedores();
	
	public List<Proveedor> listar() {
		return entityManager()
				.createQuery("from Proveedor", Proveedor.class)
				.getResultList();
	}

	public Proveedor buscar(long id) {
	    return (Proveedor) entityManager().find(Proveedor.class, id);
	}
	
	public String obtenerNombreSegunId(long idProveedor) {
		Proveedor proveedor = buscar(idProveedor);
		return proveedor.getNombre();
	}
		
}
