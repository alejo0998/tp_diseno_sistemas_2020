package main;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.after;
import org.apache.log4j.BasicConfigurator;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import controllers.*;


public class Routes {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		Spark.port(8080);
		Spark.staticFileLocation("/public");
		
		after((request, response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.closeEntityManager();
		});

		LoginController loginController = new LoginController();
		ProveedoresController proveedoresController = new ProveedoresController();
		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
		CategoriasController categoriasController = new CategoriasController(loginController);
		EntidadesController entidadesController = new EntidadesController(loginController);
		HomeController homeController = new HomeController();
	    BandejaMensajesController mensajesController = new BandejaMensajesController(loginController);
	    EgresosController egresosController = new EgresosController(loginController, proveedoresController);
		
		Spark.get("/home", (req, res) -> homeController.getHome(engine));
		Spark.get("/", (req, res) -> loginController.getFormularioLogin(engine, ""));
		Spark.get("/Categorias", (req, res) -> categoriasController.obtenerCategorias(req, res, engine));
		Spark.get("/Categorias/:id", (req, res) -> entidadesController.obtenerEntidadesDeCategoria(req, res, engine));
		Spark.get("/BandejaMensajes", (req, res) -> mensajesController.getMensajes(req, res, engine));
		//Spark.get("/BandejaMensajes/:id", (req, res) -> mensajesController.getMensajesID(req, res, engine));
		Spark.get("/Egresos", (req, res) -> egresosController.getEgresos(req, res, engine));
		Spark.get("/Entidades", (req, res) -> entidadesController.todasLasEntidades(req, res, null, engine));
		//Spark.get("/Egresos/:id", (req, res) -> egresosController.egresosProveedor(req, res, engine));
//		Spark.get("/Egresos/nueva-compra/:id", (req, res) -> egresosController.formularioCompra(req, res, engine)); 
//		Spark.get("/Egresos/nueva-compra/:id", (req, res) -> egresosController.formularioItem(req, res, engine));
		Spark.get("/BandejaMensajes/:id", (req, res) -> mensajesController.enviarMensaje(req, res, engine));
		 
		Spark.get("/Egresos/nueva-compra", (req, res) -> egresosController.formularioCompra(req, res, engine)); 
		Spark.get("/Entidades/nueva-juridica", (req, res) -> entidadesController.formularioEntidadJuridica(req, res, engine));
		Spark.get("/Entidades/nueva-base", (req, res) -> entidadesController.formularioEntidadBase(req, res, engine));
		
		Spark.post("/", (req, res) -> loginController.loguearUsuario(req, res, engine));
		Spark.post("/Categorias/:id", (req, res) -> entidadesController.asociarEntidadACategoria(req, res, engine));
		Spark.post("/Egresos/nueva-compra", (req, res) -> egresosController.agregarCompra(req, res, engine));
//		Spark.post("/Egresos/nueva-compra/:id", (req, res) -> egresosController.agregarItem(req, res, engine));
		Spark.post("/Entidades/nueva-juridica", (req, res) -> entidadesController.agregarEntidadJuridica(req, res, engine));
		Spark.post("/Entidades/nueva-base", (req, res) -> entidadesController.agregarEntidadBase(req, res, engine));
		//Spark.post("/BandejaMensajes/:id", (req, res) -> mensajesController.enviarMensaje(req, res, engine));
	}
}
