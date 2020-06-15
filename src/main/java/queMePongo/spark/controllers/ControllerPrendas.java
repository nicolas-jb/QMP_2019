package queMePongo.spark.controllers;

import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.prendas.*;
import queMePongo.spark.utils.PersistUtils;
import queMePongo.spark.utils.StringParser;
import queMePongo.spark.wrappers.prendas.AtributosPrendaWrapper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;

public class ControllerPrendas implements ControllerBase {

    private BorradorPrenda borradorPrenda;

    public ModelAndView primerPasoCrearPrenda(Request req, Response res) {
        getUsuarieLogeado(req, res);
        req.session().attribute("guardarropasId", req.params("guardarropasId"));

        return new ModelAndView(new AtributosPrendaWrapper(), "primerPasoCrearPrenda.hbs");
    }

    public ModelAndView segundoPasoCrearPrenda(Request req, Response res) {
        getUsuarieLogeado(req, res);

        TipoPrenda tipoPrenda = borradorPrenda.getTipoPrenda();
        AtributosPrendaWrapper atributos = new AtributosPrendaWrapper(tipoPrenda);

        return new ModelAndView(atributos, "segundoPasoCrearPrenda.hbs");
    }

    public ModelAndView tercerPasoCrearPrenda(Request req, Response res) {
        getUsuarieLogeado(req, res);
        req.session().attribute("guardarropasId", req.params("guardarropasId"));

        return new ModelAndView(new AtributosPrendaWrapper(), "tercerPasoCrearPrenda.hbs");
    }

    public ModelAndView guardarPrimerosPasos(Request req, Response res) {
        getUsuarieLogeado(req, res);
        Map<String, String> params = StringParser.parseBody(req.body());

        String nombrePrenda = params.get("nombrePrenda");
        TipoPrenda tipoPrenda = new AtributosPrendaWrapper().getTipoPrenda(params.get("tipo"));
        String imagen = params.get("pic");

        borradorPrenda = new BorradorPrenda(tipoPrenda);
        borradorPrenda.definirNombre(nombrePrenda);
        if(imagen != null) borradorPrenda.agregarFoto(StringParser.replaceCommonErrors(imagen));

        res.redirect("/guardarropas/" + req.session().attribute("guardarropasId") + "/prendaPaso2");
        return null;
    }

    public ModelAndView guardarSegundosPasos(Request req, Response res) {
        getUsuarieLogeado(req, res);
        Map<String, String> params = StringParser.parseBody(req.body());

        Material material = Material.valueOf(params.get("material").toUpperCase());
        Trama trama = Trama.valueOf(params.get("trama").toUpperCase());

        borradorPrenda.definirTrama(trama);
        borradorPrenda.definirMaterial(material);

        res.redirect("/guardarropas/" + req.session().attribute("guardarropasId") + "/prendaPaso3");
        return null;
    }

    public ModelAndView guardarTercerosPasos(Request req, Response res) {
        getUsuarieLogeado(req, res);
        long guardarropasId = Long.parseLong(req.session().attribute("guardarropasId"));
        Guardarropa guardarropas = getEntityManager().find(Guardarropa.class, guardarropasId);
        Map<String, String> params = StringParser.parseBody(req.body());

        Color colorPrimario = Color.valueOf(params.get("colorPrimario").toUpperCase());
        Color colorSecundario = null;
        if(params.get("colorSecundario") != null) {
            colorSecundario = Color.valueOf(params.get("colorSecundario").toUpperCase());
        }

        borradorPrenda.definirColorPrimario(colorPrimario);
        borradorPrenda.definirColorSecundario(colorSecundario);
        Prenda prendaCargada = borradorPrenda.crearPrenda();
        guardarropas.agregarPrenda(prendaCargada);

        //PersistUtils.persistObjects(guardarropas); //TODO: Hacer repositorio

        res.redirect("/guardarropas/" + req.session().attribute("guardarropasId"));
        return null;
    }
}
