package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import atributos.*;
import excepciones.CompraInvalidaException;
import inicioSesion.CreadorDeUsuarios;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import compras.Compra;
import compras.Proveedor;
import inicioSesion.TipoUsuario;
import item.Item;
import item.TipoItem;
import pagos.MedioDePago;
import pagos.MetodoPago;
import presupuestos.*;
import usuario.Usuario;

public class PresupuestoTest {

    private Compra compraUno;
    private Compra compraDos;

    private Item itemUno;
    private Item itemDos;
    private Item itemUnoPresupuestoUno;
    private Item itemDosPresupuestoUno;
    private Item itemUnoPresupuestoDos;
    private Item itemDosPresupuestoDos;
    private Item itemUnoPresupuestoTres;
    private Item itemDosPresupuestoTres;

    private List<Item> productos;
    private List<Item> productosPresupuestoUno;
    private List<Item> productosPresupuestoDos;
    private List<Item> productosPresupuestoTres;

    private Proveedor vendedor;
    private MedioDePago medioDePago;
    private Pais ejemploPais;
    private Provincia ejemploProvincia;
    private Ciudad ejemploCiudad;
    private Direccion ejemploDireccion;
    private DireccionPostal ejemploDireccionPostal;
    private Presupuesto presupuestoUno;
    private Presupuesto presupuestoDos;
    private Presupuesto presupuestoTres;
    private ValidadorDeCompras validadorCompras;
    private CreadorDeUsuarios creadorDeUsuarios;
    private List<ValidacionCompra> listaValidaciones = new ArrayList<>();

    @Before
    public void init(){

    	Moneda pesosArgentinos = new Moneda("Pesos Argentinos", "ARS", "%$", 1);
        medioDePago = new MedioDePago(MetodoPago.TARJETA_DE_CREDITO, "1234");
        ejemploPais = new Pais("Argentina", "01");
        ejemploProvincia = new Provincia("Buenos Aires", "01");
        ejemploCiudad = new Ciudad("Olivos", "01");
        ejemploDireccion = new Direccion("Malaver",2,2,2);
        ejemploDireccionPostal = new DireccionPostal(ejemploPais, ejemploProvincia, ejemploCiudad, ejemploDireccion);

        //creo vendedor
        vendedor = new Proveedor("Tramontina", 331234567, ejemploDireccionPostal);

        //creo usuario
        creadorDeUsuarios = new CreadorDeUsuarios();
        Usuario usuarioTest = creadorDeUsuarios.crearUsuario("prueba", "Pasmds1375fdfdf7", TipoUsuario.ADMINISTRADOR);
        List <Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioTest);

        //creo productos
        itemUno = new Item(TipoItem.PRODUCTO, "Se utiliza para comer", 10, 200, pesosArgentinos);
        itemDos = new Item(TipoItem.PRODUCTO, "Se utiliza para caminar", 10, 300, pesosArgentinos);
        productos = new ArrayList<>(Arrays.asList(itemUno, itemDos));
        itemUnoPresupuestoUno = new Item(TipoItem.PRODUCTO, "Se utiliza para comer", 10, 200, pesosArgentinos);
        itemDosPresupuestoUno = new Item(TipoItem.PRODUCTO, "Se utiliza para caminar", 10, 300, pesosArgentinos);
        itemUnoPresupuestoDos = new Item(TipoItem.PRODUCTO, "Se utiliza para comer", 100.0, 200, pesosArgentinos);
        itemDosPresupuestoDos = new Item(TipoItem.PRODUCTO, "Se utiliza para caminar", 200.0, 300, pesosArgentinos);
        itemUnoPresupuestoTres = new Item(TipoItem.PRODUCTO, "Se utiliza para comer", 1.0, 200, pesosArgentinos);
        itemDosPresupuestoTres = new Item(TipoItem.PRODUCTO, "Se utiliza para caminar", 1.0, 300, pesosArgentinos);

        productosPresupuestoUno = new ArrayList<>(Arrays.asList(itemUnoPresupuestoUno, itemDosPresupuestoUno));
        productosPresupuestoDos = new ArrayList<>(Arrays.asList(itemUnoPresupuestoDos, itemDosPresupuestoDos));
        productosPresupuestoTres = new ArrayList<>(Arrays.asList(itemUnoPresupuestoTres, itemDosPresupuestoTres));

        //creo compras
        compraUno = new Compra(productos, medioDePago, vendedor,2,usuarios);
        compraDos = new Compra(productos, medioDePago, vendedor,3,usuarios);

        //creo presupuestos para las compras

        presupuestoUno = new Presupuesto(productosPresupuestoUno, vendedor);
        presupuestoDos = new Presupuesto(productosPresupuestoDos, vendedor);
        presupuestoTres = new Presupuesto(productosPresupuestoTres, vendedor);

        //asigno presupuestos a las compras
        compraUno.agregarPresupuesto(presupuestoUno);
        compraUno.agregarPresupuesto(presupuestoDos);
        compraDos.agregarPresupuesto(presupuestoDos);
        compraDos.agregarPresupuesto(presupuestoTres);

        //creo validadores de las compras
        validadorCompras = new ValidadorDeCompras();

        //agrego usuario revisor
        compraUno.setRevisoresCompra(usuarios);
        compraDos.setRevisoresCompra(usuarios);
    }

    @Test
    public void compraTieneTodosLosPresupuestosCargados() {
        listaValidaciones.add(new ValidacionCompraTieneCantidadDePresupuestos());
        validadorCompras.setListaValidaciones(listaValidaciones);
        validadorCompras.validarCompra(compraUno);
    }

    @Test
    public void compraNoTieneTodosLosPresupuestosCargados() {
        listaValidaciones.add(new ValidacionCompraTieneCantidadDePresupuestos());
		validadorCompras.setListaValidaciones(listaValidaciones);
    	try {
            validadorCompras.validarCompra(compraDos);
    	} catch (CompraInvalidaException e) {
    		final String mensaje = "La compra es invalida porque: la compra no tiene la cantidad requerida de presupuestos.";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }

    @Test
    public void compraSeRealizoEnBaseAAlgunPresupuesto() {
        listaValidaciones.add(new ValidacionCompraBasadaEnPresupuesto());
        validadorCompras.setListaValidaciones(listaValidaciones);
        validadorCompras.validarCompra(compraUno);
    }

    @Test
    public void compraNoSeRealizoEnBaseAAlgunPresupuesto() {
        listaValidaciones.add(new ValidacionCompraBasadaEnPresupuesto());
        validadorCompras.setListaValidaciones(listaValidaciones);
    	try {
            validadorCompras.validarCompra(compraDos);
    	} catch (CompraInvalidaException e) {
    		final String mensaje = "La compra es invalida porque: la compra no esta basada en sus presupuestos.";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }

    @Test
    public void compraSeRealizoEnBaseACriterioMenorValor() {
        listaValidaciones.add(new ValidacionCompraCriterioMenorValor());
        validadorCompras.setListaValidaciones(listaValidaciones);
        validadorCompras.validarCompra(compraUno);
    }

    @Test
    public void compraNoSeRealizoEnBaseACriterioMenorValor() {
        listaValidaciones.add(new ValidacionCompraCriterioMenorValor());
        validadorCompras.setListaValidaciones(listaValidaciones);
    	try {
            validadorCompras.validarCompra(compraDos);
    	} catch (CompraInvalidaException e) {
    		final String mensaje = "La compra es invalida porque: la compra no se realizo en base al presupuesto de menor valor.";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }

}