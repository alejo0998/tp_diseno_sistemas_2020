package presupuestos;

import compras.Compra;

public interface ValidacionCompra {

    boolean validacionCompra(Compra compra);
    String mensajeException();

}
