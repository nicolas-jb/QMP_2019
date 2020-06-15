package queMePongo.arena.view;

import org.uqbar.commons.model.annotations.Observable;
import queMePongo.model.repositorios.RepoDeEventos;
import queMePongo.model.usuaries.Evento;

import java.util.List;

@Observable
public class FormVentanaPrincipal {
	
	private String initialDate;
	private String endDate;
	private List<Evento> eventos;
	
	public FormVentanaPrincipal() {
		this.eventos = RepoDeEventos.getInstance().getEventos();
	}
	
	public List<Evento> eventosEntre(String initialDate, String endDate) {
		return RepoDeEventos.getInstance().getEventosEntreDosFechas(initialDate, endDate);
	}
	
	public void buscar() {
		this.eventos = eventosEntre(initialDate, endDate);
	}
	
	public boolean algunaDeLasFechasEsInvalida() {
		return initialDate == null || endDate == null;
	}
	
	public String getInitialDate() {
		return initialDate;
	}
	
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public List<Evento> getEventos() {
		return this.eventos;
	}
}
