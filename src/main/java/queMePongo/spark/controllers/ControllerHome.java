package queMePongo.spark.controllers;

import queMePongo.model.usuaries.Usuarie;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerHome implements ControllerBase {

	public ModelAndView getHome(Request request, Response response) {
		Usuarie user = getUsuarieLogeado(request,response);
		return new ModelAndView(user, "home.hbs");
	}
	
	public ModelAndView noPathRedirect(Request request, Response response) {
		response.redirect("/login");
		return null;
	}
	
	
}
