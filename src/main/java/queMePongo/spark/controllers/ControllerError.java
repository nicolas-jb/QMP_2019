package queMePongo.spark.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerError implements ControllerBase {
    
    public ModelAndView mostrarError(Request req, Response res) {
        return new ModelAndView(null, "error.hbs");
    }
}
