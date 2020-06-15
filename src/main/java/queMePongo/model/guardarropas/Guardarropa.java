package queMePongo.model.guardarropas;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import queMePongo.model.alertas.Notificador;
import queMePongo.model.excepciones.GuardarropaLlenoException;
import queMePongo.model.prendas.Prenda;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.Usuarie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
public class Guardarropa {
	@Id
	@GeneratedValue
	private long id;
	
	@OneToMany
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Prenda> prendas = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	private Capacidad capacidad = Capacidad.LIMITADA;
	@Transient
	private Reservador reservador = Reservador.getInstance();
	@Transient
	private GeneradorDeSugerencias generadorDeSugerencias = GeneradorDeSugerencias.getInstance();
	private String nombre;
	
	public Guardarropa(Capacidad capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre.replaceAll("\\+", " ");
	}

	public int getCantidadDePrendas() {
		return this.prendas.size();
	}
	
	public void agregarPrenda(Prenda unaPrenda) {
		if (!capacidad.puedeAgregarPrenda(prendas.size())) throw new GuardarropaLlenoException();
		prendas.add(unaPrenda);
	}
	
	public List<Sugerencia> generarSugerencias(Usuarie usuarie, Evento evento) {
		List<Prenda> prendasDisponibles = getPrendasDisponiblesParaUnaFecha(evento.getFecha());
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		em.getTransaction().begin();
		evento = em.find(Evento.class, evento.getId());
		usuarie = em.find(Usuarie.class, usuarie.getId());
		List<Sugerencia> sugerencias = generadorDeSugerencias.generarSugerencias(prendasDisponibles, usuarie);
		evento.agregarSugerencias(sugerencias);
		sugerencias.forEach(em::persist);
		em.merge(evento);
		em.merge(usuarie);
		em.getTransaction().commit();
		Notificador.notificarNuevaSugerencia(usuarie, sugerencias);
		return sugerencias;
	} //agrego al usuario en la sugerencia para que use su sensibilidad
	
	public List<Prenda> getPrendasDisponiblesParaUnaFecha(LocalDate fecha) {
		return reservador.filtrarPrendasDisponibles(fecha, prendas);
	}
	
	public void mejorarAPremium() {
		this.capacidad = Capacidad.ILIMITADA;
	}
	
	public void degradarAGratuito() {
		this.capacidad = Capacidad.LIMITADA;
	}
}
