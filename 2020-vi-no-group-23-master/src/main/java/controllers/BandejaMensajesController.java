package controllers;





import java.util.HashMap;
import java.util.List;

import compras.Proveedor;
import repositorios.RepositorioCategorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

public class BandejaMensajesController {
	
	LoginController loginController;
	ProveedoresController proveedoresController;
	
	public BandejaMensajesController(LoginController inyected) {
		loginController = inyected;
	}

	public Object getMensajes(Request request, Response response, TemplateEngine engine) {
		
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		Long idUsuario = request.session().attribute("cookieIdUsuario");

		modelo.put("id_usuario", idUsuario);
		return engine.render(new ModelAndView(modelo, "BandejaMensajes.html.hbs"));
	
	}
	
	
	public Object getMensajesID(Request request, Response response, TemplateEngine engine) {
		
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		Long idUsuario = request.session().attribute("cookieIdUsuario");

		modelo.put("id", idUsuario);
		return engine.render(new ModelAndView(modelo, "BandejaMensajes.html.hbs"));

		
	
	}
		public Object enviarMensaje(Request request, Response response, TemplateEngine engine) {
		
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();

		
		List<Proveedor> proveedores = proveedoresController.listar();
		modelo.put("proveedores", proveedores);
		

		return engine.render(new ModelAndView(modelo, "BandejaMensajes.html.hbs"));

		
	
	}

	
	
	

}
