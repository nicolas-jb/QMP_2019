package queMePongo.model.repositorios;


import queMePongo.model.excepciones.NoSePuedeParsearLaFecha;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.EventoRepetitivo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepoDeEventos {
	
	
	private static RepoDeEventos ourInstance = new RepoDeEventos();
	
	public static RepoDeEventos getInstance() {
		return ourInstance;
	}
	
	private RepoDeEventos() {
	}
	
	
	private List<EventoRepetitivo> eventoRepetitivos = new ArrayList<>();
	private List<Evento> eventos = Arrays.asList(
			new Evento(parseDate("22/05/2019"), "Codear"),
			new Evento(parseDate("22/06/2019"), "Codear Mucho"),
			new Evento(parseDate("22/07/2019"), "Codear Hasta Morir")
	);
	
	public void notificarUsuaries() {
		eventoRepetitivos.forEach(evento -> evento.suceder());
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public void addEvento(Evento evento) {
		eventos.add(evento);
	}
	
	public void addEventoRepetitivo(EventoRepetitivo eventoRepetitivo) {
		eventoRepetitivos.add(eventoRepetitivo);
	}
	
	public List<Evento> getEventosEntreDosFechas(String fechaInicial, String fechaFinal) {
		return getEventosEntreDosFechas(parseDate(fechaInicial), parseDate(fechaFinal));
	}
	
	public List<Evento> getEventosEntreDosFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
		return eventos.stream()
				.filter(evento -> !evento.getFecha().isAfter(fechaFinal) && !evento.getFecha().isBefore(fechaInicial))
				.collect(Collectors.toList());
	}
	
	
	
	private LocalDate parseDate(String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			return LocalDate.parse(fecha, formatter);
		} catch (Exception e) {
			throw new NoSePuedeParsearLaFecha(fecha);
		}
	}
}
