package auxiliares;

import atributos.Ciudad;
import atributos.Direccion;
import atributos.DireccionPostal;
import atributos.Pais;
import atributos.Provincia;

public class CreadorDireccionesPostales {
	
	private int codigoPostal;
	private String calle;
	private int altura;
	private int piso = 0;
	private int departamento = 0;
	
	private AgenteDeTerritorios agenteDeTerritorios = null;
	
	public void agregarProveedorDeTerritorios(AgenteDeTerritorios proveedor) {
		this.agenteDeTerritorios = proveedor;
	}
	
	public void agregarCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	public void agregarDireccion(String calle, int altura, int piso, int departamento) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
	}
	
	public DireccionPostal obtenerDireccionPostal() {
		Pais pais = agenteDeTerritorios.obtenerPais(codigoPostal);
		Provincia provincia = agenteDeTerritorios.obtenerProvincia(codigoPostal);
		Ciudad ciudad = agenteDeTerritorios.obtenerCiudad(codigoPostal);
		Direccion direccion = new Direccion(calle, altura, piso, departamento);
		DireccionPostal direccionPostal = new DireccionPostal(pais, provincia, ciudad, direccion);
		return direccionPostal;
	}
}