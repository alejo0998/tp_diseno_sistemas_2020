package presupuestos;

import compras.Compra;
import compras.EstadoValidacion;
import excepciones.CompraInvalidaException;
import item.Item;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.min;

public class ValidacionCompraCriterioMenorValor implements ValidacionCompra{

    public boolean validacionCompra(Compra compra) throws CompraInvalidaException {

        if (compra.requierePresupuesto() && compra.getPresupuestos().size() > 0) {

            //busco el menor valor de los presupuestos
            List<Double> totalesPresupuestos = compra
                    .getPresupuestos()
                    .stream()
                    .map(Presupuesto::obtenerTotal)
                    .collect(Collectors.toList());

            double menorValorPresupuesto = min(totalesPresupuestos);

            double valorCompra = compra.calcularValorCompra();
            if (valorCompra != menorValorPresupuesto) {
                compra.notificarRevisores(this.mensajeException());
                throw new CompraInvalidaException(this.mensajeException());
            } else {
                //busco el presupuesto
                for (Presupuesto presupuesto : compra.getPresupuestos()) {
                    if (this.seBasoEn(compra, presupuesto)){
                        if (valorCompra == presupuesto.obtenerTotal()) {
                            compra.setEstadoValidacion(EstadoValidacion.APROBADA);
                            compra.notificarRevisores("Exito");
                            return true;
                        } else {
                            compra.setEstadoValidacion(EstadoValidacion.RECHAZADA);
                            compra.notificarRevisores(this.mensajeException());
                            throw new CompraInvalidaException(this.mensajeException());
                        }
                    }
                }
            }
        }
        compra.setEstadoValidacion(EstadoValidacion.APROBADA);
        compra.notificarRevisores("Exito");
        return true;
    }

    public String mensajeException() {
        return "la compra no se realizo en base al presupuesto de menor valor.";
    }

    private boolean seBasoEn(Compra compra, Presupuesto presupuesto) {
        //cheque si es igual el proveedor
        if (compra.getProveedor().getId() != presupuesto.getProveedor().getId()) {
            return false;
        }
        //chequeo si son iguales los items
        for(Item productoCompra : compra.getItems()) {
            int i = 0;
            boolean encontrado = false;
            while (i < presupuesto.items.size() && !encontrado) {
                boolean condicion1 = productoCompra.getTipoItem() == presupuesto.items.get(i).getTipoItem();
                boolean condicion2 = productoCompra.getCantidad() == presupuesto.items.get(i).getCantidad();
                boolean condicion3 = productoCompra.getDescripcion().equals(presupuesto.items.get(i).getDescripcion());
                boolean condicion4 = productoCompra.getValorUnitario() == presupuesto.items.get(i).getValorUnitario();
                boolean condicion5 = productoCompra.getMoneda() == presupuesto.items.get(i).getMoneda();
                if (condicion1 && condicion2 && condicion3 && condicion4 && condicion5) {
                    encontrado = true;
                }
                i++;
            }
            if (!encontrado) {
                return false;
            }
        }
        return true;
    }

}
