package auxiliares;

import atributos.Pais;
import atributos.Provincia;
import atributos.Ciudad;

public interface AgenteDeTerritorios {

	Pais obtenerPais(int codigoPostal);
	Provincia obtenerProvincia(int codigoPostal);
	Ciudad obtenerCiudad(int codigoPostal);

}
