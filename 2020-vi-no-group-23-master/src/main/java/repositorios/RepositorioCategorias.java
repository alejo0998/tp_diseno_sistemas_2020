package repositorios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import categoria.Categoria;

public class RepositorioCategorias implements WithGlobalEntityManager{
	
	public static RepositorioCategorias instancia = new RepositorioCategorias();
	
	public List<Categoria> listar() {
		return entityManager()
				.createQuery("from Categoria", Categoria.class)
				.getResultList();
	}
	
	public String obtenerNombreSegunId(long id) {
		return entityManager().find(Categoria.class, id).getNombre();
	}
	
	public Categoria buscar(long id) {
	    return entityManager().find(Categoria.class, id);
	}

}
