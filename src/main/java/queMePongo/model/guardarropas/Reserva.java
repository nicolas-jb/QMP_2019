package queMePongo.model.guardarropas;

import lombok.Data;
import lombok.NoArgsConstructor;
import queMePongo.model.prendas.Prenda;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
public class Reserva {
	
	@GeneratedValue
	@Id
	private long id;
	
	private String fecha;
	@OneToMany
	private List<Prenda> prendasReservadas = new ArrayList<>();
	
	public Reserva(LocalDate fecha) {
		setFecha(fecha.toString());
	}
	
	public List<Prenda> filtrarPrendasDisponibles(List<Prenda> prendas) {
		return prendas.stream().filter(prenda -> !prendasReservadas.contains(prenda)).collect(Collectors.toList());
	}
	
	public void reservar(Prenda prenda) {
		if (!prendasReservadas.contains(prenda))
			prendasReservadas.add(prenda);
	}
	
}
