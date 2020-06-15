package queMePongo.model.usuaries;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.uqbar.commons.model.annotations.Observable;
import queMePongo.model.excepciones.FaltaComponenteException;
import queMePongo.model.guardarropas.Sugerencia;
import queMePongo.spark.utils.DateParser;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Característica_Evento")
@Entity
@Observable
@Data
@NoArgsConstructor
public class Evento {
	@Id
	@GeneratedValue
	private long id;
	
	protected String fecha;
	private String nombre;
	private String descripcion;
	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Usuarie> invitados = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "sugerencia_evento_id")
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private List<Sugerencia> sugerencias = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "sugerenciaAceptada_evento_id")
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private List<Sugerencia> sugerenciasAceptadas = new ArrayList<>();
	
	public Evento(LocalDate fecha, String nombre) {
		if(nombre == null) throw new FaltaComponenteException();
		this.fecha = DateParser.parseToString(fecha);
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre.replaceAll("\\+", " ");
	}

	public void agregarSugerencias(List<Sugerencia> sugerencias) {
		this.sugerencias.addAll(sugerencias);
	}
	
	public void agregarSugerencia(Sugerencia sugerencia) {
		this.sugerencias.add(sugerencia);
	}
	
	public void agregarSugerenciaAceptada(Sugerencia sugerencia){
		this.sugerenciasAceptadas.add(sugerencia);
	}
	
	public void addInvitado(Usuarie invitado) {
		invitados.add(invitado);
	}
	
	public Boolean sucedeEnMenosDeTantosDias(Integer dias) {
		LocalDate fechaP = DateParser.parseDate(this.fecha);
		return fechaP.isAfter(LocalDate.now().minusDays(1)) &&
				fechaP.isBefore(LocalDate.now().plusDays(dias+1L));
	}
	
	public List<Sugerencia> getSugerenciasDeUnUsuario(Usuarie usuarie) {
		return sugerencias.stream().filter(sugerencia -> usuarie.equals(sugerencia.getUsuarie())).collect(Collectors.toList());
	}
	
	public Sugerencia getSugerenciaAceptadaDeUnUsuario(Usuarie usuarie) {
		return sugerenciasAceptadas.stream().filter(sugerencia -> usuarie.equals(sugerencia.getUsuarie())).findFirst().orElse(null);
	}
	
	@Override
	public String toString() {
		return "Evento { fecha: " + fecha + " nombre : " + nombre + "}";
	}
	
	public LocalDate getFecha(){
		return DateParser.parseDate(fecha);
	}
}
