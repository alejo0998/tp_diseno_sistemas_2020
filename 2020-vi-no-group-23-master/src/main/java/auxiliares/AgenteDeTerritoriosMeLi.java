package auxiliares;

import com.google.gson.Gson;

import atributos.Ciudad;
import atributos.Pais;
import atributos.Provincia;
import excepciones.TerritorioInvalidoException;

public class AgenteDeTerritoriosMeLi implements AgenteDeTerritorios {

    private ProveedorTerritoriosApi proveedorDeTerritorios;
    Gson gson = new Gson();

    public AgenteDeTerritoriosMeLi(ProveedorTerritoriosApi proveedorDeTerritorios){
        this.proveedorDeTerritorios = proveedorDeTerritorios;
    }
    
    public Pais obtenerPais(int codigoPostal) {
    	String stringPaisApi = proveedorDeTerritorios.obtenerPais(codigoPostal);
    	Pais paisObtenido = gson.fromJson(stringPaisApi, Pais.class);
    	if (paisObtenido.obtenerNombre() == null || paisObtenido.obtenerNombre().contentEquals("")) {
    		System.out.println("No se pudo obtener el nombre del pais. Ver https://api.mercadolibre.com/countries/AR/zip_codes/" + codigoPostal);
            throw new TerritorioInvalidoException();
        }
    	return paisObtenido;
    }
    
    public Provincia obtenerProvincia(int codigoPostal) {
    	String stringProvinciaApi = proveedorDeTerritorios.obtenerProvincia(codigoPostal);
    	Provincia provinciaObtenida = gson.fromJson(stringProvinciaApi, Provincia.class);
    	if (provinciaObtenida.obtenerNombre() == null || provinciaObtenida.obtenerNombre().contentEquals("")) {
    		System.out.println("No se pudo obtener el nombre de la provincia. Ver https://api.mercadolibre.com/countries/AR/zip_codes/" + codigoPostal);
            throw new TerritorioInvalidoException();
        }
    	return provinciaObtenida;
    }
    
    public Ciudad obtenerCiudad(int codigoPostal) {
    	String stringCiudadApi = proveedorDeTerritorios.obtenerCiudad(codigoPostal);
    	Ciudad ciudadObtenida = gson.fromJson(stringCiudadApi, Ciudad.class);
    	if (ciudadObtenida.obtenerNombre() == null || ciudadObtenida.obtenerNombre().contentEquals("")) {
    		System.out.println("No se pudo obtener el nombre de la ciudad. Ver https://api.mercadolibre.com/countries/AR/zip_codes/" + codigoPostal);
            throw new TerritorioInvalidoException();
        }
    	return ciudadObtenida;
    }
}