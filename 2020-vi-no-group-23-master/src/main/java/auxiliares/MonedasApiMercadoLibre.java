package auxiliares;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class MonedasApiMercadoLibre implements ProveedorMonedasApi {

    private static final String API_MERCADO_LIBRE = "https://api.mercadolibre.com";

    public String obtenerMonedaJson(String id) {
        ClientResponse respuestaApi = obtenerInfoMoneda(id);
        return respuestaApi.getEntity(String.class);
    }

    private ClientResponse obtenerInfoMoneda(String id) {
        Client client = Client.create();
        WebResource recurso = client.resource(API_MERCADO_LIBRE).path("/currencies/" + id);
        WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
        return builder.get(ClientResponse.class);
    }
}