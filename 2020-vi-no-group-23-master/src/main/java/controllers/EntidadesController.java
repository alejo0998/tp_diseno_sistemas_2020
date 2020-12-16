package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import categoria.Categoria;
import entidades.CategoriaEntidad;
import entidades.EntidadBase;
import entidades.EntidadJuridica;
import entidades.Organizacion;
import repositorios.RepositorioCategorias;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class EntidadesController implements WithGlobalEntityManager {
	
	LoginController loginController;
	
	public EntidadesController(LoginController inyected) {
		loginController = inyected;
	}

	public Object todasLasEntidades(Request request, Response response, String mensajeOperacionCorrecta, HandlebarsTemplateEngine engine) {
		loginController.verificarLogin(request, response, engine);
		
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("mensajeOperacionCorrecta", mensajeOperacionCorrecta);
		
		return todasLasEntidadesConAtributos(modelo, engine);
	}
	
	private Object todasLasEntidadesConAtributos(HashMap<String, Object> modelo, HandlebarsTemplateEngine engine) {
		modelo.put("entidadesJuridicas", RepositorioEntidades.instancia.listar("EntidadJuridica", EntidadJuridica.class));
		modelo.put("entidadesBase", RepositorioEntidades.instancia.listar("EntidadBase", EntidadBase.class));
		return engine.render(new ModelAndView(modelo, "Entidades.html.hbs"));
	}
	
	public Object getOrganizaciones(Request request, Response response, TemplateEngine engine) {
		HashMap<String, Object> modelo = new HashMap<>();
		List<Organizacion> organizaciones = RepositorioEntidades.instancia.listarOrganizaciones();
		modelo.put("todasLasOrganizaciones", organizaciones);
		return engine.render(new ModelAndView(modelo, "EntidadJuridica-Carga.html.hbs"));
	}
	

	public Object obtenerEntidadesDeCategoria(Request request, Response response, TemplateEngine engine) {
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		Long idCategoria = (long) Integer.parseInt(request.params(":id"));

		modelo.put("nombreCategoria", RepositorioCategorias.instancia.obtenerNombreSegunId(idCategoria));

		List<?> entidadesJuridicas = RepositorioEntidades.instancia.listarSegunCategoria(idCategoria, "EntidadJuridica",
				EntidadJuridica.class);
		modelo.put("entidadesJuridicas", entidadesJuridicas);
		if (entidadesJuridicas.size() == 0) {
			modelo.put("errorJuridicas", "No hay entidades juridicas con esta categoria");
		} else {
			modelo.put("todasLasEntidadesJuridicas", entidadesJuridicas);
		}

		List<?> entidadesBase = RepositorioEntidades.instancia.listarSegunCategoria(idCategoria, "EntidadBase",
				EntidadBase.class);
		modelo.put("entidadesBase", entidadesBase);
		if (entidadesBase.size() == 0) {
			modelo.put("errorBase", "No hay entidades base con esta categoria");
		} else {
			modelo.put("todasLasEntidadesBase", entidadesBase);
		}

		return engine.render(new ModelAndView(modelo, "EntidadesPorCategoria.html.hbs"));
	}

	public Object asociarEntidadACategoria(Request req, Response res, TemplateEngine engine) {
		long idCategoria = (long) Integer.parseInt(req.params(":id"));
		String idEntidadJuridica = req.queryParams("entidadJuridica_id");

		if (idEntidadJuridica != null && !idEntidadJuridica.equals("null")) {
			RepositorioEntidades.instancia.agregarACategoria(idCategoria, idEntidadJuridica, EntidadJuridica.class);
		}

		String idEntidadBase = req.queryParams("entidadBase_id");
		if (idEntidadBase != null && !idEntidadBase.equals("null")) {
			RepositorioEntidades.instancia.agregarACategoria(idCategoria, idEntidadBase, EntidadBase.class);
		}

		return obtenerEntidadesDeCategoria(req, res, engine);
	}

	public Object formularioEntidadJuridica(Request request, Response response, HandlebarsTemplateEngine engine) {
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		
		CategoriaEntidad[] tipos = entidades.CategoriaEntidad.class.getEnumConstants();
		List<String> tiposEnString = new ArrayList<>();
		for (CategoriaEntidad tipo : tipos) {
			tiposEnString.add(tipo.getValor());
		}
		modelo.put("tipos", tipos);

		List<Categoria> categorias = RepositorioCategorias.instancia.listar();
		modelo.put("todasLasCategorias", categorias);
		List<EntidadBase> entidadesBases = RepositorioEntidades.instancia.listarEntidadesBase();
		modelo.put("todasLasEntidadesBase", entidadesBases);
		List<Organizacion> organizaciones = RepositorioEntidades.instancia.listarOrganizaciones();
		modelo.put("todasLasOrganizaciones", organizaciones);
		return engine.render(new ModelAndView(modelo, "EntidadesJuridicas-Carga.html.hbs"));
	}

	public Object agregarEntidadJuridica(Request req, Response res, HandlebarsTemplateEngine engine) {
		EntidadJuridica nuevaEntidad = null;
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		String nombreFicticio = req.queryParams("nombreFicticio");
		String razonSocial = req.queryParams("razonSocial");
		long cuit = (long) Integer.parseInt(req.queryParams("cuit"));
		int codigoPostal = Integer.parseInt(req.queryParams("codigoPostal"));

		CategoriaEntidad tipo = null;
		if (!req.queryParams("tipo_id").equals("null")) {
			tipo = CategoriaEntidad.valueOf(req.queryParams("tipo_id"));
		}
		
		entityManager().getTransaction().begin();
		nuevaEntidad = new EntidadJuridica(nombreFicticio, razonSocial, cuit, codigoPostal, entidadesBase, tipo);

		if (!req.queryParams("categoria_id").equals("null")) {
			long idCategoria = (long) Integer.parseInt(req.queryParams("categoria_id"));
			RepositorioEntidades.instancia.agregarACategoria(idCategoria, nuevaEntidad);
		}
		//while(!req.queryParams("entidadBase_id").equals("null")) {
		if (!req.queryParams("entidadBase_id").equals("null")) {
			long idEntidadBase = (long) Integer.parseInt(req.queryParams("entidadBase_id"));
			RepositorioEntidades.instancia.agregarAEntidadJuridica(idEntidadBase, nuevaEntidad);
		}
		if (!req.queryParams("organizacion_id").equals("null")) {
			long idOrganizacion = (long) Integer.parseInt(req.queryParams("organizacion_id"));
			RepositorioEntidades.instancia.agregarAOrganizacion(idOrganizacion, nuevaEntidad);
		}
		
		entityManager().persist(nuevaEntidad);
		entityManager().getTransaction().commit();

		String resultadoOk = "La entidad juridica \"" + nombreFicticio + "\" se creo correctamente!";
		return todasLasEntidades(req, res, resultadoOk, engine);
	}

	public Object formularioEntidadBase(Request request, Response response, HandlebarsTemplateEngine engine) {
		loginController.verificarLogin(request, response, engine);
		HashMap<String, Object> modelo = new HashMap<>();
		List<Categoria> categorias = RepositorioCategorias.instancia.listar();
		modelo.put("todasLasCategorias", categorias);
		List<Organizacion> organizaciones = RepositorioEntidades.instancia.listarOrganizaciones();
		modelo.put("todasLasOrganizaciones", organizaciones);
		return engine.render(new ModelAndView(modelo, "EntidadesBase-Carga.html.hbs"));
	}

	public Object agregarEntidadBase(Request req, Response res, HandlebarsTemplateEngine engine) {
		String nombreFicticio = req.queryParams("nombreFicticio");
		String descripcion = req.queryParams("descripcion");
		entityManager().getTransaction().begin();
		EntidadBase nuevaEntidad = new EntidadBase(nombreFicticio, descripcion, null);

		if (!req.queryParams("categoria_id").equals("null")) {
			long idCategoria = (long) Integer.parseInt(req.queryParams("categoria_id"));
			RepositorioEntidades.instancia.agregarACategoria(idCategoria, nuevaEntidad);
		}
		if (!req.queryParams("organizacion_id").equals("null")) {
			long idOrganizacion = (long) Integer.parseInt(req.queryParams("organizacion_id"));
			RepositorioEntidades.instancia.asignarAEntidadBase(idOrganizacion, nuevaEntidad);
		}
		entityManager().persist(nuevaEntidad);
		entityManager().getTransaction().commit();
		String resultadoOk = "La entidad base \"" + nombreFicticio + "\" se creo correctamente!";
		return todasLasEntidades(req, res, resultadoOk, engine);
	}
}