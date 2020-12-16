package repositorios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import categoria.Categoria;
import etiqueta.Etiqueta;

public class RepositorioEtiquetas implements WithGlobalEntityManager{
	
	public static RepositorioEtiquetas instancia = new RepositorioEtiquetas();
	
	public List<Etiqueta> listar() {
		return entityManager()
				.createQuery("from Etiqueta", Etiqueta.class)
				.getResultList();
	}
	
	public Etiqueta buscar(long id) {
	    return entityManager().find(Etiqueta.class, id);
	}

}