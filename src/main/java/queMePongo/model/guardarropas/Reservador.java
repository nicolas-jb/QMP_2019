package queMePongo.model.guardarropas;

import org.hibernate.annotations.Cascade;
import queMePongo.model.prendas.Prenda;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reservador {
	@Id
	@GeneratedValue
	private long id;
	@OneToMany
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Reserva> reservas = new ArrayList<>();
	
	protected Reservador() {
	}
	
	@Transient
	private static Reservador instance = new Reservador();
	
	public static Reservador getInstance() {
		return instance;
	}
	
	public void reservar(LocalDate fecha, Prenda prenda) {
		obtenerReserva(fecha).reservar(prenda);
	}
	
	public List<Prenda> filtrarPrendasDisponibles(LocalDate fecha, List<Prenda> prendas) {
		return obtenerReserva(fecha).filtrarPrendasDisponibles(prendas);
	}
	
	public Reserva obtenerReserva(LocalDate fecha) {
		return reservas.stream()
				.filter(unaReserva -> fecha.equals(unaReserva.getFecha()))
				.findAny().orElse(crearReserva(fecha));
	}
	
	private Reserva crearReserva(LocalDate fecha) {
		Reserva nuevaReserva = new Reserva(fecha);
		reservas.add(nuevaReserva);
		return nuevaReserva;
	}
}
