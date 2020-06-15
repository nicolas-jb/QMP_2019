package queMePongo.spark.controllers;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import queMePongo.model.guardarropas.Sugerencia;
import queMePongo.model.repositorios.jpa.RepositorioDeEventos;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.Usuarie;
import queMePongo.spark.utils.StringParser;
import queMePongo.spark.wrappers.calendario.CalendarWeekWrapper;
import queMePongo.spark.wrappers.calendario.CalendarWrapper;
import queMePongo.spark.wrappers.calendario.DiaCalendarioEventosWrapper;
import queMePongo.spark.wrappers.calendario.TodayWrapper;
import queMePongo.spark.wrappers.sugerencias.EventoSugerenciasWrapper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;
import static queMePongo.model.repositorios.jpa.RepositorioDeEventos.getEventosDeUnUsuario;
import static queMePongo.spark.utils.DateParser.dateFormat;
import static queMePongo.spark.utils.DateParser.parseDate;

public class ControllerEventos implements ControllerBase {

    public ModelAndView verUnEvento(Request request, Response response) {
        Usuarie usuarie = getUsuarieLogeado(request, response);
        Evento evento = getEntityManager().find(Evento.class, Long.parseLong(request.params("eventoId")));
    
        if (evento.getSugerenciaAceptadaDeUnUsuario(usuarie) != null) {
            return new ModelAndView(evento, "evento-con-prendas-aceptadas.hbs");
        } else if (!evento.getSugerenciasDeUnUsuario(usuarie).isEmpty()) {
            return new ModelAndView(evento, "evento-con-sugerencias.hbs");
        } else {
            return new ModelAndView(evento, "evento-sin-sugerencias.hbs");
        }
    }

    public ModelAndView wizardCreacionEventoNuevo(Request req, Response res) {
        getUsuarieLogeado(req, res);
        TodayWrapper hoy = new TodayWrapper();

        return new ModelAndView(hoy, "nuevoEvento.hbs");
    }

    public ModelAndView postEvento(Request request, Response response) {
        Usuarie usuarie = getUsuarieLogeado(request, response);
        Map<String, String> bodyParams = StringParser.parseBody(request.body());
        String nombreEvento = bodyParams.get("nombreEvento");
        LocalDate fechaEvento = parseDate(bodyParams.get("fechaEvento"));
        Evento evento = new Evento(fechaEvento, nombreEvento);
        evento.addInvitado(usuarie);
        EntityManager em = PerThreadEntityManagers.getEntityManager();
        em.getTransaction().begin();
        em.persist(evento);
        em.getTransaction().commit();
        response.redirect("/calendar");
        return null;
    }

    public ModelAndView showCalendar(Request request, Response response) {
        Usuarie usuarie = getUsuarieLogeado(request, response);
        LocalDate date = LocalDate.now();
        List<DiaCalendarioEventosWrapper> diasCalendario = getDiasCalendario(date.getMonth(), date.getYear())
                .stream()
                .map(fecha -> new DiaCalendarioEventosWrapper(fecha.getDayOfMonth(), getEventosDeUnUsuario(usuarie, fecha)))
                .collect(Collectors.toList());
        Integer weeks = diasCalendario.size() / 7;
        List<CalendarWeekWrapper> weekWrappers = new ArrayList<>();
        for (int i = 0; i < weeks; i++) {
            List<DiaCalendarioEventosWrapper> diasSemana = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                diasSemana.add(diasCalendario.get(7 * i + j));
            }
            weekWrappers.add(new CalendarWeekWrapper(diasSemana));
        }

        return new ModelAndView(new CalendarWrapper(weekWrappers, date.getMonth().name(), date.getYear()), "calendar.hbs");
    }

    private List<LocalDate> getDiasCalendario(Month mes, int anio) {
        List<LocalDate> diasDelCalendario = getUltimosDiasDelMesAnterior(mes, anio);
        diasDelCalendario.addAll(getDiasDelMes(mes, anio));
        diasDelCalendario.addAll(getPrimerosDiasDelMesSiguiente(mes, anio));
        return diasDelCalendario;
    }

    private List<LocalDate> getDiasDelMes(Month mes, int anio) {
        List<LocalDate> diasDelMes = new ArrayList<>();
        for (int i = 1; i <= mes.minLength(); i++) {
            diasDelMes.add(parseDate(String.format(dateFormat(), anio, mes.getValue(), i)));
        }
        return diasDelMes;
    }

    private List<LocalDate> getUltimosDiasDelMesAnterior(Month mesActual, int anio) {
        int daysToComplete = parseDate(anio + "-" + mesActual.getValue() + "-" + 1).getDayOfWeek().getValue() - 1;
        Month mesAnterior = mesActual.minus(1);
        int diasDelAnterior = mesAnterior.minLength();
        List<LocalDate> ultimosDiasDelMesAnterior = new ArrayList<>();
        for (int i = daysToComplete - 1; i >= 0; i--) {
            ultimosDiasDelMesAnterior.add(parseDate(String.format(dateFormat(), anio, mesAnterior.getValue(), diasDelAnterior - i)));
        }
        return ultimosDiasDelMesAnterior;
    }

    private List<LocalDate> getPrimerosDiasDelMesSiguiente(Month mesActual, int anio) {
        int lastDay = mesActual.minLength();
        int daysToComplete = 7 - parseDate(anio + "-" + mesActual.getValue() + "-" + lastDay).getDayOfWeek().getValue();
        Month mesSiguiente = mesActual.plus(1);
        List<LocalDate> primerosDiasDelMesSiguiente = new ArrayList<>();
        for (int i = 1; i <= daysToComplete; i++) {
            primerosDiasDelMesSiguiente.add(parseDate(String.format(dateFormat(), anio, mesSiguiente.getValue(), i)));
        }
        return primerosDiasDelMesSiguiente;
    }

}
