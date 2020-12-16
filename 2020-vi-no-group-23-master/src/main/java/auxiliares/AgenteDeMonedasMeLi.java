package auxiliares;

import atributos.Moneda;
import com.google.gson.Gson;
import excepciones.MonedaInvalidaException;

public class AgenteDeMonedasMeLi implements AgenteDeMonedas {

    private ProveedorMonedasApi proveedorDeMoneda;

    public AgenteDeMonedasMeLi(ProveedorMonedasApi proveedorMoneda) {
        proveedorDeMoneda = proveedorMoneda;
    }

    public Moneda obtenerMoneda(String id) {
        String stringApi = proveedorDeMoneda.obtenerMonedaJson(id);

        Gson gson = new Gson();

        Moneda moneda = gson.fromJson(stringApi, Moneda.class);

        if (moneda.getDescripcion() == null) {
            throw new MonedaInvalidaException();
        }

        return moneda;
    }
}