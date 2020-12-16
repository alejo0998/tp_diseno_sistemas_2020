package principal;

import compras.Compra;
import compras.EstadoValidacion;
import entidades.Organizacion;
import presupuestos.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main (String[] args) {
        //obtengo la organizacion
        Organizacion organizacion = null;

        //creo validador de las compras
        ValidadorDeCompras validadorCompras = new ValidadorDeCompras();
        List<ValidacionCompra> listaValidaciones = new ArrayList<>();
        listaValidaciones.add(new ValidacionCompraTieneCantidadDePresupuestos());
        listaValidaciones.add(new ValidacionCompraBasadaEnPresupuesto());
        listaValidaciones.add(new ValidacionCompraCriterioMenorValor());
        validadorCompras.setListaValidaciones(listaValidaciones);

        //valido las compras pendientes de validacion
//        for (Compra compra : organizacion.getRegistroEgresos()) {
//            if (compra.getEstadoValidacion() == EstadoValidacion.PENDIENTE_EVALUACION) {
//                validadorCompras.validarCompra(compra);
//            }
//        }
    } 

}