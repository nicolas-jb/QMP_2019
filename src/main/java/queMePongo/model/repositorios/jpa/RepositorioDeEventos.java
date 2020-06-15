package queMePongo.model.repositorios.jpa;

import lombok.NoArgsConstructor;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.Usuarie;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers.getEntityManager;

@NoArgsConstructor
public class RepositorioDeEventos {
	
	public static List<Evento> getEventosDeUnUsuario(Usuarie usuarie) {
		List<Evento> todosLosEventos = getEntityManager().createQuery("from Evento e").getResultList();
		return todosLosEventos.stream()
				.filter(evento -> evento.getInvitados().contains(usuarie))
				.collect(Collectors.toList());
	}
	
	public static List<Evento> getEventosDeUnUsuario(Usuarie usuarie, LocalDate fecha) {
		return getEventosDeUnUsuario(usuarie)
				.stream()
				.filter(evento -> fecha.equals(evento.getFecha()))
				.collect(Collectors.toList());
	}
}
