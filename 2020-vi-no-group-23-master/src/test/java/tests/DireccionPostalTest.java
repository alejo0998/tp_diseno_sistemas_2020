package tests;
import org.junit.*;

import atributos.*;
import auxiliares.*;
import org.mockito.Mockito;
import excepciones.TerritorioInvalidoException;

public class DireccionPostalTest {

	private CreadorDireccionesPostales construirDireccionPostal;
	
	@Before
	public void setup() {
		construirDireccionPostal = new CreadorDireccionesPostales();
	}
	
    @Test
    public void apiDevuelveDireccionPostal_obtengoDireccionPostal(){
		ProveedorTerritoriosApi proveedorTerritoriosApiMock = Mockito.mock(ProveedorTerritoriosApi.class);

		Mockito.when(proveedorTerritoriosApiMock.obtenerPais(1842))
				.thenReturn("{\n" +
						"  \"id\": \"AR\",\n" +
						"  \"name\": \"Argentina\"\n" +
						"}");
		Mockito.when(proveedorTerritoriosApiMock.obtenerProvincia(1842))
				.thenReturn("{\n" +
						"  \"id\": \"AR-B\",\n" +
						"  \"name\": \"Buenos Aires\"\n" +
						"}");
		Mockito.when(proveedorTerritoriosApiMock.obtenerCiudad(1842))
				.thenReturn("{\n" +
						"  \"id\": \"MG\",\n" +
						"  \"name\": \"Monte Grande\"\n" +
						"}");

		AgenteDeTerritorios agenteDeTerritorios = new AgenteDeTerritoriosMeLi(proveedorTerritoriosApiMock);
		construirDireccionPostal.agregarProveedorDeTerritorios(agenteDeTerritorios);
		construirDireccionPostal.agregarCodigoPostal(1842);
		DireccionPostal direccionPostal = construirDireccionPostal.obtenerDireccionPostal();
    	Assert.assertEquals("Monte Grande", direccionPostal.obtenerCiudad().obtenerNombre());
    }
    
	@Test
	public void agenteDevuelveDireccionPostal_obtengoDireccionPostal() {
		AgenteDeTerritorios agenteDeTerritorios = Mockito.mock(AgenteDeTerritorios.class);

		Pais argentina = new Pais("Argentina", "AR");
		Provincia buenosAires = new Provincia("Buenos Aires", "BA");
		Ciudad lanus = new Ciudad("Lanus", "LN");
		Mockito.when(agenteDeTerritorios.obtenerPais(1845))
				.thenReturn(argentina);
		Mockito.when(agenteDeTerritorios.obtenerProvincia(1845))
				.thenReturn(buenosAires);
		Mockito.when(agenteDeTerritorios.obtenerCiudad(1845))
				.thenReturn(lanus);

		construirDireccionPostal.agregarProveedorDeTerritorios(agenteDeTerritorios);
		construirDireccionPostal.agregarCodigoPostal(1845);
		construirDireccionPostal.agregarDireccion("Ferrero", 1234, 0, 0);
		DireccionPostal direccionPostal = construirDireccionPostal.obtenerDireccionPostal();

		Assert.assertEquals("Buenos Aires", direccionPostal.obtenerProvincia().obtenerNombre());
    }
	
	@Test(expected = TerritorioInvalidoException.class)
	public void codigoPostalInvalido_devuelveCodigoPostalInvalidoException() {
		ProveedorTerritoriosApi proveedorTerritoriosApiMock = Mockito.mock(ProveedorTerritoriosApi.class);

		Mockito.when(proveedorTerritoriosApiMock.obtenerPais(1842))
				.thenReturn("{\n" +
						"  \"id\": \"\",\n" +
						"  \"name\": \"\"\n" +
						"}");
		Mockito.when(proveedorTerritoriosApiMock.obtenerProvincia(1842))
				.thenReturn("{\n" +
						"  \"id\": \"\",\n" +
						"  \"name\": \"\"\n" +
						"}");
		Mockito.when(proveedorTerritoriosApiMock.obtenerCiudad(1842))
				.thenReturn("{\n" +
						"  \"id\": \"\",\n" +
						"  \"name\": \"\"\n" +
						"}");

		AgenteDeTerritorios agenteDeTerritorios = new AgenteDeTerritoriosMeLi(proveedorTerritoriosApiMock);
		construirDireccionPostal.agregarProveedorDeTerritorios(agenteDeTerritorios);
		construirDireccionPostal.agregarCodigoPostal(1842);
		construirDireccionPostal.obtenerDireccionPostal();
	}
}
