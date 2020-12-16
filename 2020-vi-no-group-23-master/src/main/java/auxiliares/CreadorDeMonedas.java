package auxiliares;

import atributos.Moneda;

public class CreadorDeMonedas {

    private AgenteDeMonedas agenteDeMonedas;

    public void agregarProveedorDeMonedas(AgenteDeMonedas agenteDeMonedas) {
        this.agenteDeMonedas = agenteDeMonedas;
    }

    public Moneda obtenerMoneda(String id) {
        return agenteDeMonedas.obtenerMoneda(id);
    }
}