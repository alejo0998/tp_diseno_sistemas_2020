package controllers;

import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Response;
import spark.Request;
import spark.TemplateEngine;
import spark.template.handlebars.HandlebarsTemplateEngine;
import usuario.Usuario;

import java.util.HashMap;

public class LoginController {

	public Object getFormularioLogin(TemplateEngine engine, String error) {
		HashMap<String, String> modelo = new HashMap<>();
		modelo.put("errorCredenciales", error);
		return engine.render(new ModelAndView(modelo, "home.html.hbs"));
	}

	public Object loguearUsuario(Request req, Response res, HandlebarsTemplateEngine engine) {
		String password = req.queryParams("password");
		String username = req.queryParams("user");
		Usuario usuario = RepositorioUsuarios.instancia.obtenerUsuarioPorCredenciales(password, username);

		if (usuario != null) {
			req.session().attribute("cookieIdUsuario", usuario.getId());
			res.redirect("/home");
		} else {
			return getFormularioLogin(engine, "Credenciales incorrectas");
		}
		return null;
	}

	public void verificarLogin(Request request, Response response, TemplateEngine engine) {
		Long idUsuario = request.session().attribute("cookieIdUsuario");
		Usuario usuario = null;

		if (idUsuario != null) {
			usuario = RepositorioUsuarios.instancia.getById(idUsuario);
		}

		if (usuario == null) {
			response.redirect("/home");
		}
	}
}
