package queMePongo.spark.controllers;

import queMePongo.model.atuendos.Calificacion;
import queMePongo.model.guardarropas.Sugerencia;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.Usuarie;
import queMePongo.spark.utils.StringParser;
import queMePongo.spark.wrappers.sugerencias.EventoSugerenciasWrapper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;

public class ControllerSugerencias implements ControllerBase {
	
	public ModelAndView verSugerencias(Request request, Response response) {
        Usuarie usuarie = getUsuarieLogeado(request, response);
        Evento evento = getEntityManager().find(Evento.class, Long.parseLong(request.params("eventoId")));
        List<Sugerencia> sugerencias = evento.getSugerencias().stream().filter(sugerencia -> usuarie.equals(sugerencia.getUsuarie())).collect(Collectors.toList());
        return new ModelAndView(new EventoSugerenciasWrapper(evento, sugerencias), "sugerencias.hbs");
    }

    public ModelAndView aceptarSugerencia(Request request, Response response) {
        Usuarie usuarie = getUsuarieLogeado(request, response);
        getEntityManager().getTransaction().begin();
        Evento evento = getEntityManager().find(Evento.class, Long.parseLong(request.params("eventoId")));
	    Map<String,String> params = StringParser.parseBody(request.body());
	    Sugerencia sugerencia = getEntityManager().find(Sugerencia.class, Long.parseLong(params.get("sugerenciaId")));
        evento.agregarSugerenciaAceptada(sugerencia);
        getEntityManager().merge(evento);
	    getEntityManager().getTransaction().commit();
	    response.redirect("/eventos/" + evento.getId());
        return null;
    }

    public ModelAndView verSugerenciasAceptadas(Request request,Response response){
        Usuarie usuarie = getUsuarieLogeado(request,response);
        Evento evento = getEntityManager().find(Evento.class, Long.parseLong(request.params("eventoId")));
        List<Sugerencia> sugerenciasAceptadas = evento.getSugerenciasAceptadas().stream().filter(sugerencia -> sugerencia.getUsuarie().equals(usuarie)).collect(Collectors.toList());
        return new ModelAndView(new EventoSugerenciasWrapper(evento,sugerenciasAceptadas), "aceptadas.hbs");
    }

    public ModelAndView formCalificaciones(Request request, Response response){
        Usuarie usuarie = getUsuarieLogeado(request,response);
        Evento evento = getEntityManager().find(Evento.class, Long.parseLong(request.params("eventoId")));
        //falta
        return null;
    }
    
    public ModelAndView calificar(Request request, Response response) {
    	Usuarie usuarie = getUsuarieLogeado(request, response);
	    Evento evento = getEntityManager().find(Evento.class, Long.parseLong(request.params("eventoId")));
	    int calificacion = Integer.parseInt(StringParser.parseBody(request.body()).get("calificacion"));
	    getEntityManager().getTransaction().begin();
	    Sugerencia sugerencia = getEntityManager().find(Sugerencia.class, Long.parseLong(request.params("sugerenciaId")));
	    sugerencia.calificar(Calificacion.getCalificacion(calificacion));
	    getEntityManager().merge(sugerencia);
	    getEntityManager().getTransaction().commit();
	    response.redirect("/eventos/" + evento.getId());
	    return null;
    }
}
