package presupuestos;

import compras.Compra;
import compras.EstadoValidacion;
import excepciones.CompraInvalidaException;

public class ValidacionCompraTieneCantidadDePresupuestos implements ValidacionCompra {

    public boolean validacionCompra(Compra compra) throws CompraInvalidaException {
        if (compra.requierePresupuesto() && (compra.getPresupuestos().size() != compra.getCantidadPresupuestos())) {
            compra.setEstadoValidacion(EstadoValidacion.RECHAZADA);
            compra.notificarRevisores(this.mensajeException());
            throw new CompraInvalidaException(this.mensajeException());
        }
        compra.setEstadoValidacion(EstadoValidacion.APROBADA);
        compra.notificarRevisores("Exito");
        return true;
    }

    public String mensajeException() {
        return "la compra no tiene la cantidad requerida de presupuestos.";
    }

}
