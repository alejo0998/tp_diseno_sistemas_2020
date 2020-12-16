package tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import categoria.*;
import entidades.*;
import excepciones.*;
import reglasNegocio.*;

public class CategoriasTest {
	
	private EntidadBase baseConLimiteDeEgresosDisponible;
	private EntidadBase baseSinCategoria;
	private EntidadBase baseSinLimiteDeEgresosDisponible;
	private EntidadBase basePuedeSerParteJuridica;
	private EntidadBase baseNoPuedeSerParteJuridica;
	
	private EntidadJuridica juridicaConBase;
	private EntidadJuridica juridicaSinBase;
	private EntidadJuridica juridicaAceptaEntidadBase;
	
	private Categoria entidadConEgresosLimitados;
	private Categoria noAceptaEntidadBase;
	private Categoria noPuedeSerParteEntidadJuridica;
	private ReglaNegocio reglaLimiteEgresos;
	private ReglaNegocio reglaSinEntidadBase;
	private ReglaNegocio reglaBaseSinJuridica;
	private List<ReglaNegocio> reglasDeEntidadConEgresosLimitados;
	private List<ReglaNegocio> reglasDeEntidadSinEntidadBase;
	private List<ReglaNegocio> reglasDeEntidadBaseSinJuridica;
	
	private Organizacion organizacion;
	
	@Before
	public void init(){
		
		reglaLimiteEgresos = new AceptarNuevosEgresos(2);
		reglasDeEntidadConEgresosLimitados = new ArrayList<ReglaNegocio>();
		reglasDeEntidadConEgresosLimitados.add(reglaLimiteEgresos);
		entidadConEgresosLimitados = new Categoria("Entidad con egresos limitados", reglasDeEntidadConEgresosLimitados);
		
		organizacion = new Organizacion(null,null);
		
		reglaSinEntidadBase = new NoPuedeAgregarEntidadBase();
		reglasDeEntidadSinEntidadBase = new ArrayList<ReglaNegocio>();
		reglasDeEntidadSinEntidadBase.add(reglaSinEntidadBase);
		noAceptaEntidadBase = new Categoria("Entidad jurídica sin entidades base", reglasDeEntidadSinEntidadBase);
		
		reglaBaseSinJuridica = new NoPuedeSerParteDeEntidadJuridica();
		reglasDeEntidadBaseSinJuridica = new ArrayList<ReglaNegocio>();
		reglasDeEntidadBaseSinJuridica.add(reglaBaseSinJuridica);
		noPuedeSerParteEntidadJuridica = new Categoria("Entidad base no puede ser parte de entidad juridica", reglasDeEntidadBaseSinJuridica);
		
		baseConLimiteDeEgresosDisponible = new EntidadBase("Zapateria pepe", "Vende zapatos",organizacion);
		baseSinCategoria = new EntidadBase("Supermercado xingxing", "Los chinos",organizacion);
		baseSinLimiteDeEgresosDisponible = new EntidadBase("Cafeteria pepa", "Vende cafe",organizacion);
		basePuedeSerParteJuridica = new EntidadBase("Farmacia rosa", "Vende medicamentos", organizacion);
		baseNoPuedeSerParteJuridica = new EntidadBase("Bomberos", "Bomberos", organizacion);
		
		List<EntidadBase> listaEntidadesBase = new ArrayList<EntidadBase>();
		juridicaConBase = new EntidadJuridica("Distribuidora Juan", "Juan SA", 24356547764L, 1567, listaEntidadesBase, CategoriaEntidad.PEQUENIA);
		juridicaSinBase = new EntidadJuridica("Kiosco Jose", "Jose SA", 24353337764L, 1527, listaEntidadesBase, CategoriaEntidad.MICRO);
		juridicaAceptaEntidadBase = new EntidadJuridica("Colegio", "Colegio SA", 24353999764L, 1127, listaEntidadesBase, CategoriaEntidad.MICRO);
	}
	
    @Test
    public void aceptarNuevoEgresoSiNoTieneCategoria(){
    	Assert.assertTrue(baseSinCategoria.puedeAceptarNuevoEgreso());
    }
	
    @Test
    public void aceptarNuevoEgresoSiNoSuperoLimite(){
    	baseConLimiteDeEgresosDisponible.agregarCategoria(entidadConEgresosLimitados);
    	Assert.assertTrue(baseConLimiteDeEgresosDisponible.puedeAceptarNuevoEgreso());
    }
    
    @Test
    public void rechazarNuevoEgresoSiSuperoLimite(){
    	baseSinLimiteDeEgresosDisponible.agregarCategoria(entidadConEgresosLimitados);
    	baseSinLimiteDeEgresosDisponible.sumarEgreso();
    	baseSinLimiteDeEgresosDisponible.sumarEgreso();
    	Assert.assertFalse(baseSinLimiteDeEgresosDisponible.puedeAceptarNuevoEgreso());
    }
    
    @Test
    public void aceptarNuevaEntidadBase(){
    	Assert.assertTrue(juridicaConBase.puedeAceptarNuevaEntidadBase());
    }
    
    @Test
    public void rechazarNuevaEntidadBase(){
    	juridicaSinBase.agregarCategoria(noAceptaEntidadBase);
    	try {
    		juridicaSinBase.agregarEntidadAsociada(baseSinCategoria);
    	} catch (EntidadException e) {
    		final String mensaje = "La operación sobre la entidad es inválida porque: Tiene una categoría que no permite agregar una entidad base";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
    
    @Test
    public void entidadBasePuedeSerParteDeJuridica(){
    	juridicaAceptaEntidadBase.agregarEntidadAsociada(basePuedeSerParteJuridica);
    	Assert.assertTrue(juridicaConBase.contieneEntidadBase(basePuedeSerParteJuridica));
    }
    
    @Test
    public void entidadBaseNoPuedeSerParteDeJuridica(){
    	baseNoPuedeSerParteJuridica.agregarCategoria(noPuedeSerParteEntidadJuridica);
    	try {
    		juridicaAceptaEntidadBase.agregarEntidadAsociada(baseNoPuedeSerParteJuridica);
    	} catch (EntidadException e) {
    		final String mensaje = "La operación sobre la entidad es inválida porque: La entidad base no puede ser parte de una entidad jurídica";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
}
