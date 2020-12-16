package db;

import static org.junit.Assert.*;
import inicioSesion.TipoUsuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import etiqueta.Etiqueta;
import usuario.Usuario;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	

	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
	
	@Test
	public void testPrueba() {
		Etiqueta etiquetaPrueba = new Etiqueta("Probando");
		Usuario usuario = new Usuario("alejo123","estoyProbando",null);
		entityManager().persist(etiquetaPrueba);

		entityManager().persist(usuario);
		
	}
	
	


}
