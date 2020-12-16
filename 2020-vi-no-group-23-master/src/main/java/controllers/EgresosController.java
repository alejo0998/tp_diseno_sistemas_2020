package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import atributos.Moneda;

import compras.Compra;
import compras.Proveedor;
import entidades.EntidadBase;
import entidades.EntidadJuridica;
import etiqueta.Etiqueta;
import item.Item;
import item.TipoItem;
import pagos.MedioDePago;
import pagos.MetodoPago;
import repositorios.RepositorioCategorias;
import repositorios.RepositorioEgresos;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioEtiquetas;
import repositorios.RepositorioItems;
import repositorios.RepositorioProveedores;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;
import spark.template.handlebars.HandlebarsTemplateEngine;
import usuario.Usuario;

public class EgresosController implements WithGlobalEntityManager {

	LoginController loginController;
	ProveedoresController proveedoresController;


	
	public EgresosController(LoginController loginController, ProveedoresController proveedoresController) {
		this.loginController = loginController;
		this.proveedoresController = proveedoresController;
	}

	public Object getEgresos(Request request, Response response, TemplateEngine engine) {
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		List<Proveedor> proveedores = proveedoresController.listar();
		modelo.put("proveedores", proveedores);
		return engine.render(new ModelAndView(modelo, "Egresos.html.hbs"));
	}
	
	
	public Object egresosProveedor(Request request, Response response, TemplateEngine engine){
//		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		
		return todasLasComprasProveedor(request, response, engine);
	}
	
	
	public Object todasLasCompras(Request request, Response response, String mensajeOperacionCorrecta, HandlebarsTemplateEngine engine) {
		loginController.verificarLogin(request, response, engine);
		
		HashMap<String, Object> modelo = new HashMap<>();
		
		modelo.put("mensajeOperacionCorrecta", mensajeOperacionCorrecta);
		
		return engine.render(new ModelAndView(modelo, "Egresos.html.hbs"));
	}

	public Object todasLasComprasProveedor(Request request, Response response, TemplateEngine engine) {
		
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		Long idProveedor = (long) Integer.parseInt(request.params(":id"));
	
		modelo.put("nombreProveedor", RepositorioProveedores.instancia.obtenerNombreSegunId(idProveedor));
	
		//modelo.put("ComprasRealizadas", RepositorioEgresos.instancia.listarCompras());
		
		List<Compra> comprasRealizadas = RepositorioEgresos.instancia.listarSegunProveedor(idProveedor);
		modelo.put("comprasRealizadas", comprasRealizadas);
		if (comprasRealizadas.size() == 0) {
			modelo.put("errorEgresos", "No hay compras con esta categoria");
		} else {
			modelo.put("todasLasCompras", comprasRealizadas);
		}
		
	
		return engine.render(new ModelAndView(modelo, "ProveedorPorEgreso.html.hbs"));
	}
	
	
	
	
	public Object formularioCompra(Request request, Response response, HandlebarsTemplateEngine engine) {
//		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		
		MetodoPago[] tipos = pagos.MetodoPago.class.getEnumConstants();
		List<String> tiposEnString = new ArrayList<>();
		for (MetodoPago tipo : tipos) {
			tiposEnString.add(tipo.getValor());
		}
		modelo.put("tipos", tipos);
		
//		TipoItem[] tiposs = item.TipoItem.class.getEnumConstants();
//		List<String> tipossEnString = new ArrayList<>();
//		for (TipoItem tipoo : tiposs) {
//			tipossEnString.add(tipoo.getValor());
//		}
//		modelo.put("tiposs", tiposs);

		List<Proveedor> proveedores = RepositorioProveedores.instancia.listar();
		modelo.put("todosLosProveedores", proveedores);
		
//		List<Usuario> revisores = RepositorioUsuarios.instancia.listar();
//		modelo.put("todosLosUsuarios", revisores);
		List<Etiqueta> etiquetas = RepositorioEtiquetas.instancia.listar();
		modelo.put("todasLasEtiquetas", etiquetas);
//		List<Moneda> monedas = RepositorioItems.instancia.listarMonedas();
//		modelo.put("todasLasMonedas", monedas);
		List<Item> items = RepositorioItems.instancia.listar();
		modelo.put("todosLosItems", items);
		return engine.render(new ModelAndView(modelo, "Egresos-Carga.html.hbs"));
	}
	
	public Object agregarCompra(Request req, Response res, HandlebarsTemplateEngine engine) {
		Compra nuevaCompra = null;
		Collection<Item> items = null;
		Collection<Usuario> revisores = null;
		int cantidadPresupuestos = Integer.parseInt(req.queryParams("cant_presupuestos"));
		String identificador = req.queryParams("id_medioPago");
		MetodoPago tipo = null;
		if (!req.queryParams("tipo_id").equals("null")) {
			tipo = MetodoPago.valueOf(req.queryParams("tipo_id"));
		}
		
		Proveedor proveedor = null;
		if (!req.queryParams("proveedor_id").equals("null")) {
			long idProveedor = (long) Integer.parseInt(req.queryParams("proveedor_id"));
			proveedor = RepositorioProveedores.instancia.buscar(idProveedor);
		}
		
		entityManager().getTransaction().begin();
		
		MedioDePago medioDePago = new MedioDePago(tipo, identificador);

		nuevaCompra = new Compra(items, medioDePago, proveedor, cantidadPresupuestos, revisores);

//		if (!req.queryParams("usuario_id").equals("null")) {
//			long idRevisor = (long) Integer.parseInt(req.queryParams("usuario_id"));
//			RepositorioEgresos.instancia.agregarARevisores(idRevisor, nuevaCompra);
//		}
		if (!req.queryParams("etiqueta_id").equals("null")) {
			long idEtiqueta = (long) Integer.parseInt(req.queryParams("etiqueta_id"));
			RepositorioEgresos.instancia.agregarAEtiquetas(idEtiqueta, nuevaCompra);
		}
		if (!req.queryParams("item_id").equals("null")) {
			long idItem = (long) Integer.parseInt(req.queryParams("item_id"));
			RepositorioItems.instancia.agregarItem(idItem, nuevaCompra);
		}
		
//		Item nuevoItem = new Item(tipoItem, descripcion, valor_unitario, cantidad, moneda);
//		RepositorioEgresos.instancia.agregarAItems(nuevoItem, nuevaCompra);

		entityManager().persist(medioDePago);
		entityManager().persist(nuevaCompra);
		entityManager().getTransaction().commit();

		String resultadoOk = "La compra se creo correctamente!";
		return todasLasCompras(req, res, resultadoOk, engine);
	}
}
	
//	public Object formularioItem(Request request, Response response, HandlebarsTemplateEngine engine) {
//		//loginController.verificarLogin(request, response, engine);
// 		HashMap<String, Object> modelo = new HashMap<>();
//		
//		TipoItem[] tipos = item.TipoItem.class.getEnumConstants();
//		List<String> tiposEnString = new ArrayList<>();
//		for (TipoItem tipo : tipos) {
//			tiposEnString.add(tipo.getValor());
//		}
//		modelo.put("tiposItem", tipos);
//
//		List<Moneda> monedas = RepositorioItems.instancia.listarMonedas();
//		modelo.put("todasLasMonedas", monedas);
//		
//		return engine.render(new ModelAndView(modelo, "Item-Carga.html.hbs"));
//	}
//	
//	public Object agregarItem(Request req, Response res, HandlebarsTemplateEngine engine) {
//		String descripcion = req.queryParams("descripcion");
//		double valor_unitario = (long) Integer.parseInt(req.queryParams("valor_unitario"));
//		int cantidad = Integer.parseInt(req.queryParams("cantidad"));
//		long idCompra = (long) Integer.parseInt(req.params(":id"));
//		
//		TipoItem tipoItem = null;
//		if (!req.queryParams("tipo_id").equals("null")) {
//			tipoItem = TipoItem.valueOf(req.queryParams("tipo_id"));
//		}
//		Moneda moneda = null;
//		if (!req.queryParams("moneda_id").equals("null")) {
//			long idMoneda = (long) Integer.parseInt(req.queryParams("moneda_id"));
//			moneda = RepositorioItems.instancia.buscar(idMoneda);
//		}
//		
//		entityManager().getTransaction().begin();
//		
//		Item nuevoItem = new Item(tipoItem, descripcion, valor_unitario, cantidad, moneda);
//		
//		RepositorioItems.instancia.agregarItem(idCompra, nuevoItem);
//		
//		entityManager().persist(nuevoItem);
//		entityManager().getTransaction().commit();
//
//		return todasLasComprasProveedor(req, res , engine);
//	}
//	
//	
//}