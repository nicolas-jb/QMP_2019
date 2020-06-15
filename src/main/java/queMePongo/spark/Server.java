package queMePongo.spark;

import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.prendas.BorradorPrenda;
import queMePongo.model.tasks.GeneradorDeSugerenciasTask;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.Suscripcion;
import queMePongo.model.usuaries.Usuarie;
import queMePongo.spark.controllers.*;
import queMePongo.spark.utils.PersistUtils;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.staticFiles;

public class Server {
    public static void main(String[] args) {

        crearUnUsuario();
        GeneradorDeSugerenciasTask.main(null);

        Spark.port(9000);
        //Spark.port(getHerokuAssignedPort());
		Spark.staticFiles.location("/public");
        //staticFiles.externalLocation("C:\\Users\\m-pel\\Documents\\UTN\\DDS\\TP Integrador\\2019-vi-no-group-10\\src\\main\\resources\\public");
        Spark.init();

        ControllerGuardarropas controllerGuardarropas = new ControllerGuardarropas();
        ControllerLogin controllerLogin = new ControllerLogin();
        ControllerHome controllerHome = new ControllerHome();
        ControllerEventos controllerEventos = new ControllerEventos();
        ControllerError controllerError = new ControllerError();
        ControllerPrendas controllerPrendas = new ControllerPrendas();
        ControllerSugerencias controllerSugerencias = new ControllerSugerencias();

        Spark.get("/", controllerHome::noPathRedirect, new HandlebarsTemplateEngine());
        Spark.get("/home", controllerHome::getHome, new HandlebarsTemplateEngine());
        Spark.get("/login", controllerLogin::logPage, new HandlebarsTemplateEngine());
        Spark.get("/guardarropas", controllerGuardarropas::logPage, new HandlebarsTemplateEngine());
        Spark.get("/guardarropas/:id", controllerGuardarropas::mostrarPrendasDeUnGuardarropas, new HandlebarsTemplateEngine());
        Spark.get("/error", controllerError::mostrarError, new HandlebarsTemplateEngine());
        Spark.get("/guardarropa", controllerGuardarropas::creacionGuardarropasNuevo, new HandlebarsTemplateEngine());
        Spark.get("/guardarropas/:guardarropasId/prendaPaso1", controllerPrendas::primerPasoCrearPrenda, new HandlebarsTemplateEngine());
        Spark.get("/guardarropas/:guardarropasId/prendaPaso2", controllerPrendas::segundoPasoCrearPrenda, new HandlebarsTemplateEngine());
        Spark.get("/guardarropas/:guardarropasId/prendaPaso3", controllerPrendas::tercerPasoCrearPrenda, new HandlebarsTemplateEngine());
        Spark.get("/nuevoEvento", controllerEventos::wizardCreacionEventoNuevo, new HandlebarsTemplateEngine());
        Spark.get("/calendar", controllerEventos::showCalendar, new HandlebarsTemplateEngine());
        Spark.get("/eventos/:eventoId", controllerEventos::verUnEvento, new HandlebarsTemplateEngine());
        Spark.get("/eventos/:eventoId/atuendosCalificados", controllerSugerencias::formCalificaciones, new HandlebarsTemplateEngine());
        //TODO: volar
        Spark.get("/eventos/:eventoId/sugerencias", controllerSugerencias::verSugerencias, new HandlebarsTemplateEngine());
        Spark.get("/eventos/:eventoId/sugerenciasAceptadas",controllerSugerencias::verSugerenciasAceptadas,new HandlebarsTemplateEngine());

        Spark.post("/prendaPaso1", controllerPrendas::guardarPrimerosPasos, new HandlebarsTemplateEngine());
        Spark.post("/prendaPaso2", controllerPrendas::guardarSegundosPasos, new HandlebarsTemplateEngine());
        Spark.post("/prendaPaso3", controllerPrendas::guardarTercerosPasos, new HandlebarsTemplateEngine());
        Spark.post("/login", controllerLogin::logUser, new HandlebarsTemplateEngine());
        Spark.post("/guardarropa", controllerGuardarropas::postGuardarropas, new HandlebarsTemplateEngine());
        Spark.post("/eventos", controllerEventos::postEvento, new HandlebarsTemplateEngine());
        Spark.post("/eventos/:eventoId/sugerenciasAceptadas", controllerSugerencias::aceptarSugerencia, new HandlebarsTemplateEngine());
	    Spark.post("/eventos/:eventoId/sugerenciasCalificadas/:sugerenciaId", controllerSugerencias::calificar, new HandlebarsTemplateEngine());
	
	    DebugScreen.enableDebugScreen();
    }

    public static void crearUnUsuario() {
        Usuarie usuarie = new Usuarie("prueba", "pass");
        usuarie.cambiarSuscripcion(Suscripcion.PREMIUM);
        usuarie.setGuardarropas(Arrays.asList(
                crearGuardarropas("Casual"),
                crearGuardarropas("Formal")
        ));
        List<Evento> eventos = Arrays.asList(
                new Evento(LocalDate.now().plusDays(1), "EventoDeManiana"),
                new Evento(LocalDate.now(), "EventoDeHoy"),
                new Evento(LocalDate.now().plusDays(10), "EventoLejano")
        );
        eventos.forEach(evento -> evento.addInvitado(usuarie));
        PersistUtils.persistObjects(eventos.toArray());
        PersistUtils.persistObjects(usuarie);
    }


    private static Guardarropa crearGuardarropas(String nombre) {
        Guardarropa guardarropa = new Guardarropa();
        guardarropa.setNombre(nombre);
        guardarropa.setPrendas(Arrays.asList(
                BorradorPrenda.crearRemeraRandom(),
                BorradorPrenda.crearZapatoRandom(),
                BorradorPrenda.crearAccesorioRandom(),
                BorradorPrenda.crearCamperaRandom(),
                BorradorPrenda.crearPantalonRandom()));
        return guardarropa;
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }

        return 9000; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
