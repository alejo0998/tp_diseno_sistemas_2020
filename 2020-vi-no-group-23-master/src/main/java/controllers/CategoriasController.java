package controllers;

import java.util.HashMap;

import repositorios.RepositorioCategorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

public class CategoriasController {
	
	LoginController loginController;
	
	public CategoriasController(LoginController inyected) {
		loginController = inyected;
	}

	public Object obtenerCategorias(Request request, Response response, TemplateEngine engine) {
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("Categorias", RepositorioCategorias.instancia.listar());
		return engine.render(new ModelAndView(modelo, "Categorias.html.hbs"));
	}
}
