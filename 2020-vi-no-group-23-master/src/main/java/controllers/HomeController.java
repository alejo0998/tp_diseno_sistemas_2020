package controllers;

import spark.ModelAndView;
import spark.TemplateEngine;

public class HomeController {

    public Object getHome(TemplateEngine engine) {
        return engine.render(new ModelAndView(null, "index.html.hbs"));
    }
}