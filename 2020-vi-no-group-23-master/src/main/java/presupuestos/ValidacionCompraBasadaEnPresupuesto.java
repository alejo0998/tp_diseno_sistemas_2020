package presupuestos;

import compras.Compra;
import compras.EstadoValidacion;
import excepciones.CompraInvalidaException;
import item.Item;

public class ValidacionCompraBasadaEnPresupuesto implements ValidacionCompra {

    public boolean validacionCompra(Compra compra) throws CompraInvalidaException {
        if (compra.requierePresupuesto()) {
            for (Presupuesto presupuesto : compra.getPresupuestos()) {
                if (this.seBasoEn(compra, presupuesto)){
                    compra.setEstadoValidacion(EstadoValidacion.APROBADA);
                    compra.notificarRevisores("Exito");
                    return true;
                }
            }
            compra.setEstadoValidacion(EstadoValidacion.RECHAZADA);
            compra.notificarRevisores(this.mensajeException());
            throw new CompraInvalidaException(this.mensajeException());
        } else {
            compra.setEstadoValidacion(EstadoValidacion.APROBADA);
            compra.notificarRevisores("Exito");
            return true;
        }
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

    public String mensajeException() {
        return "la compra no esta basada en sus presupuestos.";
    }
}
