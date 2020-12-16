package tareasAutomaticas;

import atributos.*;
import compras.Compra;
import compras.EstadoValidacion;
import compras.Proveedor;
import inicioSesion.CreadorDeUsuarios;
import inicioSesion.TipoUsuario;
import item.Item;
import item.TipoItem;
import org.quartz.*;
import pagos.MedioDePago;
import pagos.MetodoPago;
import presupuestos.*;
import usuario.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TareaValidacionDeIngresos implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {

        Compra compraUno;
        Compra compraDos;

        Item itemUno;
        Item itemDos;
        Item itemUnoPresupuestoUno;
        Item itemDosPresupuestoUno;
        Item itemUnoPresupuestoDos;
        Item itemDosPresupuestoDos;
        Item itemUnoPresupuestoTres;
        Item itemDosPresupuestoTres;

        List<Item> productos;
        List<Item> productosPresupuestoUno;
        List<Item> productosPresupuestoDos;
        List<Item> productosPresupuestoTres;

        Proveedor vendedor;
        MedioDePago medioDePago;
        Pais ejemploPais;
        Provincia ejemploProvincia;
        Ciudad ejemploCiudad;
        Direccion ejemploDireccion;
        DireccionPostal ejemploDireccionPostal;
        Presupuesto presupuestoUno;
        Presupuesto presupuestoDos;
        Presupuesto presupuestoTres;
        ValidadorDeCompras validadorCompras;
        CreadorDeUsuarios creadorDeUsuarios;
        List<ValidacionCompra> listaValidaciones = new ArrayList<>();
        List<Compra> listaCompras = new ArrayList<>();

        Moneda pesosArgentinos = new Moneda("Pesos Argentinos", "ARS", "%$", 1);
        medioDePago = new MedioDePago(MetodoPago.TARJETA_DE_CREDITO, "1234");
        ejemploPais = new Pais("Argentina", "01");
        ejemploProvincia = new Provincia("Buenos Aires", "01");
        ejemploCiudad = new Ciudad("Olivos", "01");
        ejemploDireccion = new Direccion("Malaver",2,2,2);
        ejemploDireccionPostal = new DireccionPostal(ejemploPais, ejemploProvincia, ejemploCiudad, ejemploDireccion);

        vendedor = new Proveedor("Tramontina", 331234567, ejemploDireccionPostal);

        creadorDeUsuarios = new CreadorDeUsuarios();
        Usuario usuarioTest = creadorDeUsuarios.crearUsuario("prueba", "Pasmds1375fdfdf7", TipoUsuario.ADMINISTRADOR);
        List <Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioTest);

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

        compraUno = new Compra(productos, medioDePago, vendedor,2,usuarios);
        compraDos = new Compra(productos, medioDePago, vendedor,3,usuarios);

        presupuestoUno = new Presupuesto(productosPresupuestoUno, vendedor);
        presupuestoDos = new Presupuesto(productosPresupuestoDos, vendedor);
        presupuestoTres = new Presupuesto(productosPresupuestoTres, vendedor);

        compraUno.agregarPresupuesto(presupuestoUno);
        compraUno.agregarPresupuesto(presupuestoDos);
        compraDos.agregarPresupuesto(presupuestoDos);
        compraDos.agregarPresupuesto(presupuestoTres);

        compraUno.setRevisoresCompra(usuarios);
        compraDos.setRevisoresCompra(usuarios);

        validadorCompras = new ValidadorDeCompras();
        listaValidaciones = new ArrayList<>();
        listaValidaciones.add(new ValidacionCompraTieneCantidadDePresupuestos());
        listaValidaciones.add(new ValidacionCompraBasadaEnPresupuesto());
        listaValidaciones.add(new ValidacionCompraCriterioMenorValor());
        validadorCompras.setListaValidaciones(listaValidaciones);

        listaCompras = new ArrayList<>();
        listaCompras.add(compraUno);
        listaCompras.add(compraDos);

		for (Compra compra : listaCompras) {
			if (compra.getEstadoValidacion() == EstadoValidacion.PENDIENTE_EVALUACION) {
				validadorCompras.validarCompra(compra);
			}
		}

    }
}
