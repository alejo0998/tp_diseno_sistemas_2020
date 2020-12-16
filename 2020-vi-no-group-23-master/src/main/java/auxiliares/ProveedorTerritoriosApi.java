package auxiliares;

public interface ProveedorTerritoriosApi {
	String obtenerPais(int codigoPostal);
	String obtenerProvincia(int codigoPostal);
	String obtenerCiudad(int codigoPostal);
}