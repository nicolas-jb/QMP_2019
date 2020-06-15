package queMePongo.model.tasks;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.Usuarie;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class GeneradorDeSugerenciasTask {
	
	private static final long DAYTIME_IN_MS = 1000L * 60 * 60 * 24;
	private static final int ANTERIORIDAD_DE_SUGERENCIAS = 7;
	
	public static void main(String[] args) {
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				GeneradorDeSugerenciasTask.generarSugerenciasSiFaltanPocosDias();
			}
		}, 1000, DAYTIME_IN_MS);
		
		
	}
	
	private static void generarSugerenciasSiFaltanPocosDias() {
		List<Evento> eventos = PerThreadEntityManagers.getEntityManager().createQuery("from Evento").getResultList();
		List<Evento> eventosProntos = eventos.stream().filter(evento -> evento.sucedeEnMenosDeTantosDias(ANTERIORIDAD_DE_SUGERENCIAS)).collect(Collectors.toList());
//		Semaphore semaphore = new Semaphore(1);
//		for (Evento evento : eventosProntos) {
//			for (Usuarie usuarie : evento.getInvitados()){
//				for (Guardarropa guardarropa : usuarie.getGuardarropas()){
//					semaphore.acquireUninterruptibly();
//					guardarropa.generarSugerencias(usuarie,evento);
//					semaphore.release();
//				}
//			}
//		}
		
		for (int i=0; i< eventosProntos.size(); i++) {
			List<Usuarie> usuaries = eventosProntos.get(i).getInvitados();
			for (int j=0; j< usuaries.size(); j++){
				List<Guardarropa> guardarropas = usuaries.get(j).getGuardarropas();
				for(int k=0; k< guardarropas.size(); k++){
					guardarropas.get(k).generarSugerencias(usuaries.get(j), eventosProntos.get(i));
				}
			}
		}

//		Evento evento = PerThreadEntityManagers.getEntityManager().find(Evento.class, 1L);
//		Usuarie usuarie = evento.getInvitados().get(0);
//		usuarie.getGuardarropas().get(0).generarSugerencias(usuarie,evento);
//		usuarie.getGuardarropas().get(1).generarSugerencias(usuarie,evento);
//		eventos.stream()
//				.filter(evento -> evento.sucedeEnMenosDeTantosDias(ANTERIORIDAD_DE_SUGERENCIAS))
//				.forEach(evento -> evento.getInvitados()
//						.forEach(usuarie -> usuarie.getGuardarropas()
//								.forEach(guardarropa -> guardarropa.generarSugerencias(usuarie, evento))));
	}
}
