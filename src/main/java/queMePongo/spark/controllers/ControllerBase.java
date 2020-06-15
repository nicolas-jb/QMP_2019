package queMePongo.spark.controllers;

import queMePongo.model.usuaries.Usuarie;
import spark.Request;
import spark.Response;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;

public interface ControllerBase {
	
	default Usuarie getUsuarieLogeado(Request request, Response response) {
		Long userId = request.session().attribute("userId");
		if(userId != null){
			Usuarie usuarie = getEntityManager().find(Usuarie.class,userId);
			if(usuarie != null){
				return usuarie;
			}
		}
		redirectToLoginErrorPage(response);
		return null;
	}
	
	default void redirectToLoginErrorPage(Response response){
		response.redirect("/error");
	}
}
