package repositorios;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios implements WithGlobalEntityManager {

	public static RepositorioUsuarios instancia = new RepositorioUsuarios();

	public static List<Usuario> usuarioLogueados = new ArrayList<>();

	public List<Usuario> listar() {
		return entityManager()
				.createQuery("from Usuario", Usuario.class) //
				.getResultList();
	}
	
	public Usuario buscar(long id) {
	    return (Usuario) entityManager().find(Usuario.class, id);
	}

	public Usuario getById(Long id) {
		return entityManager().find(Usuario.class, id);
	}

	public void agregar(Usuario usuario) {
		entityManager().persist(usuario);
	}
	
	public Usuario obtenerUsuarioPorCredenciales(String password, String nombre) {
		List<Usuario> usuarios = entityManager()
		        .createQuery("from Usuario u where u.nombre = :nombre and u.contrasenia = :password", Usuario.class)
		        .setParameter("nombre", nombre)
		        .setParameter("password", password)
		        .getResultList();
		
		if (usuarios.size() > 0) {
			return usuarios.get(0);
		} else {
			return null;
		}
	}

}