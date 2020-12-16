package auxiliares;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TerritoriosApiMercadoLibre implements ProveedorTerritoriosApi{
	
	private static final String API_MERCADO_LIBRE = "https://api.mercadolibre.com";
	private Gson gson = new Gson();
	
	public String obtenerPais(int codigoPostal){
		return obtenerLocacion(codigoPostal, "country");
	}
	
	public String obtenerProvincia(int codigoPostal){
		return obtenerLocacion(codigoPostal, "state");
	}
	
	public String obtenerCiudad(int codigoPostal){
		return obtenerLocacion(codigoPostal, "city");
	}
	
	private String obtenerLocacion(int codigoPostal, String locacion){
		ClientResponse respuestaApi = obtenerLocacionPorCP(codigoPostal);
		String stringApi = respuestaApi.getEntity(String.class);
		JsonObject jsonObject = gson.fromJson(stringApi , JsonObject.class);
		return jsonObject.getAsJsonObject(locacion).toString();
	}

    private ClientResponse obtenerLocacionPorCP(int codigoPostal){
    	Client client = Client.create();
        WebResource recurso = client.resource(API_MERCADO_LIBRE).path("/countries/AR/zip_codes/" + codigoPostal);
        WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
        return builder.get(ClientResponse.class);
    }
}