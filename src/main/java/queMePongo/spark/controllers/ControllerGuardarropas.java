package queMePongo.spark.controllers;

import queMePongo.model.guardarropas.Capacidad;
import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.usuaries.Usuarie;
import queMePongo.spark.utils.PersistUtils;
import queMePongo.spark.utils.StringParser;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;

public class ControllerGuardarropas implements ControllerBase {


	public ModelAndView mostrarPrendasDeUnGuardarropas(Request req, Response res) {
		getUsuarieLogeado(req, res);
		long guardarropasId = Long.parseLong(req.params("id"));
		Guardarropa guardarropas = getEntityManager().find(Guardarropa.class, guardarropasId);
		return new ModelAndView(guardarropas, "prendas.hbs");
	}
	
	public ModelAndView logPage(Request req, Response res) {
		Usuarie usuarie = getUsuarieLogeado(req, res);
		return new ModelAndView(usuarie, "guardarropas.hbs");
	}
	
	public ModelAndView creacionGuardarropasNuevo(Request req, Response res) {
		getUsuarieLogeado(req, res);
		return new ModelAndView(null, "crear_guardarropas.hbs");
	}
	
	public ModelAndView postGuardarropas(Request req, Response res) {
		Usuarie usuarie = getUsuarieLogeado(req, res);
		Map<String, String> params = StringParser.parseBody(req.body());

		Guardarropa guardarropas = new Guardarropa(Capacidad.valueOf(params.get("capacidad").toUpperCase()));
		guardarropas.setNombre(params.get("nombre"));
		usuarie.agregarGuardarropa(guardarropas);

		PersistUtils.persistObjects(usuarie); //TODO: Se persiste asi?

		res.redirect("/guardarropas");
		return null;
	}
}
