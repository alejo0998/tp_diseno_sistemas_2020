package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import atributos.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import compras.Compra;
import compras.Proveedor;
import entidades.EntidadBase;
import entidades.EntidadJuridica;
import entidades.Organizacion;
import entidades.CategoriaEntidad;
import excepciones.CompraInvalidaException;
import item.Item;
import item.TipoItem;
import pagos.MedioDePago;
import pagos.MetodoPago;
import usuario.Usuario;

public class OrganizacionTest {

	@SuppressWarnings("unused")
	private Compra compra;
	private Item itemUno;
	private Item itemDos;
	private Proveedor vendedor;
	private EntidadJuridica entidadJuridica;
	@SuppressWarnings("unused")
	private EntidadJuridica entidadJuridicaDos;
	private EntidadBase entidadBaseUno;
	private EntidadBase entidadBaseDos;
	private List<Item> productos;
	private MedioDePago medioDePago;
	private Organizacion organizacion;
	private Pais ejemploPais;
	private Provincia ejemploProvincia;
	private Ciudad ejemploCiudad;
	private Direccion ejemploDireccion;
	private DireccionPostal ejemploDireccionPostal;
	private List <Usuario> usuarios;
	
	
	@Before
	public void init(){

    	Moneda pesosArgentinos = new Moneda("Pesos Argentinos", "ARS", "%$", 1);

		itemUno = new Item(TipoItem.PRODUCTO, "Se utiliza para comer", 10, 200, pesosArgentinos);
		itemDos = new Item(TipoItem.PRODUCTO, "Se utiliza para caminar", 10, 300, pesosArgentinos);
		
		productos = new ArrayList<Item>(Arrays.asList(itemUno, itemDos));
		
		medioDePago = new MedioDePago(MetodoPago.TARJETA_DE_CREDITO, "1234");

		ejemploPais = new Pais("Argentina", "01");
		ejemploProvincia = new Provincia("Buenos Aires", "01");
		ejemploCiudad = new Ciudad("Olivos", "01");
		ejemploDireccion = new Direccion("Malaver",2,2,2);
		ejemploDireccionPostal = new DireccionPostal(ejemploPais, ejemploProvincia, ejemploCiudad, ejemploDireccion);
		vendedor = new Proveedor("Tramontina", 331234567, ejemploDireccionPostal);
		
		entidadBaseUno = new EntidadBase("Zapateria pepe", "Vende zapatos",organizacion);
		entidadBaseDos = new EntidadBase("Supermercado xingxing", "Los chinos",organizacion);
		
		List<EntidadBase> entidadesAsociadasUno = new ArrayList<EntidadBase>(Arrays.asList(entidadBaseUno, entidadBaseDos));
		List<EntidadBase> entidadesAsociadasDos = new ArrayList<EntidadBase>(Arrays.asList());
		
		entidadJuridica = new EntidadJuridica("Fusion Asiatica", "Asociacion zapatos en supermercados", 337654321, 1742, entidadesAsociadasUno,CategoriaEntidad.MICRO);
		entidadJuridicaDos = new EntidadJuridica("Nada por ahora", "Pos nada", 1234567890, 1893, entidadesAsociadasDos,CategoriaEntidad.OSC);
		
		List<EntidadJuridica> estructuraEntidades = new ArrayList<EntidadJuridica>(Arrays.asList(entidadJuridica));
		
//		Organizacion(List <Compra> registroEgresos, List<EntidadJuridica> registroEntidadesJuridicas, List<EntidadBase> registroEntidadesBases)
		organizacion = new Organizacion("Metalurgica", estructuraEntidades);
	}
	
	//Validar excepciones en constructores
    @Test
    public void CompraInvalidaPorNoTenerProductos() {
		productos.clear();
    	try {
    		compra = new Compra(productos, medioDePago, vendedor,2,usuarios);
    	} catch (CompraInvalidaException e) {
    		final String mensaje = "La compra es invalida porque: no contiene productos";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
	
    @Test
    public void CompraInvalidaPorNoTenerMedioDePago() {
    	try {
    		compra = new Compra(productos, null, vendedor,2,usuarios);
    	} catch (CompraInvalidaException e) {
    		final String mensaje = "La compra es invalida porque: no contiene medio de pago";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
	
    @Test
    public void CompraInvalidaPorNoTenerVendedor() {
    	try {
    		compra = new Compra(productos, medioDePago, null,2,usuarios);
    	} catch (CompraInvalidaException e) {
    		final String mensaje = "La compra es invalida porque: no contiene un vendedor";
    		Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
}

