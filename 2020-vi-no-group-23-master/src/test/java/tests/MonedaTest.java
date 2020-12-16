package tests;

import atributos.Moneda;
import auxiliares.AgenteDeMonedas;
import auxiliares.AgenteDeMonedasMeLi;
import auxiliares.CreadorDeMonedas;
import auxiliares.ProveedorMonedasApi;
import excepciones.MonedaInvalidaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MonedaTest {

	private CreadorDeMonedas creadorDeMonedas;

	@Before
	public void setup() {
		creadorDeMonedas = new CreadorDeMonedas();
	}

	// En este test creo un Mock de ProveedorMonedasApi
	// En este caso, estoy probando que mi sistema pueda interpretar correctamente el JSON que devuelve la API
	// de Mercado Libre
    @Test
	public void obtenerMoneda_ApiDevuelvePesos_obtengoPesos() {
		// Creo el Mock
		ProveedorMonedasApi proveedorMonedasApiMock = Mockito.mock(ProveedorMonedasApi.class);

		// Le digo al Mock que devuelva la moneda que yo quiero en formato JSON
		// Me estoy copiando el JSON que esta en la pagina de MeLi
		Mockito.when(proveedorMonedasApiMock.obtenerMonedaJson("ARS"))
				.thenReturn("{\n" +
						"  \"id\": \"ARS\",\n" +
						"  \"description\": \"Peso argentino\",\n" +
						"  \"symbol\": \"$\",\n" +
						"  \"decimal_places\": 0\n" +
						"}");

		// Le paso el Mock al Agente de Monedas
		AgenteDeMonedas agenteDeMonedas = new AgenteDeMonedasMeLi(proveedorMonedasApiMock);

		creadorDeMonedas.agregarProveedorDeMonedas(agenteDeMonedas);

		Moneda moneda = creadorDeMonedas.obtenerMoneda("ARS");

    	Assert.assertEquals("Peso argentino", moneda.getDescripcion());
    }

	// En este test creo un Mock de AgenteDeMonedas
	@Test
	public void obtenerMoneda_AgenteDevuelvePesos_obtengoPesos() {
		// Creo el Mock
		AgenteDeMonedas agenteDeMonedas = Mockito.mock(AgenteDeMonedas.class);

		// Le digo al Mock que devuelva la moneda que yo quiero, ahora en formato objeto
		Moneda moneda = new Moneda("Descripcion", "Id", "%$", 1);
		Mockito.when(agenteDeMonedas.obtenerMoneda("Id"))
				.thenReturn(moneda);

		// Le paso el Mock al Creador de Monedas
		creadorDeMonedas.agregarProveedorDeMonedas(agenteDeMonedas);

		creadorDeMonedas.obtenerMoneda("Id");


		Assert.assertEquals("Descripcion", moneda.getDescripcion());
    }

	// En este test voy a forzar una respuesta erronea de la Api, para que arroje una excepción
	@Test(expected = MonedaInvalidaException.class)
	public void obtenerMoneda_monedaInvalida_devuelveMonedaInvalidaException() {
		// Creo el Mock
		ProveedorMonedasApi proveedorMonedasApiMock = Mockito.mock(ProveedorMonedasApi.class);

		// Le digo al Mock que devuelva la moneda que yo quiero en formato JSON
		// Me estoy copiando el JSON que esta en la pagina de MeLi
		Mockito.when(proveedorMonedasApiMock.obtenerMonedaJson("ARS"))
				.thenReturn("{\n" +
						"  \"JsonErroneo\": \"Valor sin sentido\"\n" +
						"}");

		// Le paso el Mock al Agente de Monedas
		AgenteDeMonedas agenteDeMonedas = new AgenteDeMonedasMeLi(proveedorMonedasApiMock);

		creadorDeMonedas.agregarProveedorDeMonedas(agenteDeMonedas);

		creadorDeMonedas.obtenerMoneda("ARS");
	}

}