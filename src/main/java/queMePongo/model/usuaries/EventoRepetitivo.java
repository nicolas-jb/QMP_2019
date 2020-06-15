package queMePongo.model.usuaries;

import lombok.NoArgsConstructor;
import queMePongo.spark.utils.DateParser;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@DiscriminatorValue("Repetitivo")
@Entity
@NoArgsConstructor
public class EventoRepetitivo extends Evento {
	
	private Long frecuencia;
	private String fechaFin;
	
	public EventoRepetitivo(LocalDate fecha, Long frecuencia, String nombre) {
		super(fecha, nombre);
		this.frecuencia = frecuencia;
	}
	
	public EventoRepetitivo(LocalDate fecha, Long frecuencia, String nombre, LocalDate fechaFin) {
		this(fecha, frecuencia, nombre);
		this.fechaFin = fechaFin.toString();
	}
	
	public void suceder() {
		LocalDate nuevaFecha = getFecha().plusDays(frecuencia);
		if (getFechaFin() == null || nuevaFecha.isBefore(getFechaFin())) {
			this.fecha = DateParser.parseToString(nuevaFecha);
		}
	}
	
	public LocalDate getFechaFin(){
		return DateParser.parseDate(fechaFin);
	}
	
}
