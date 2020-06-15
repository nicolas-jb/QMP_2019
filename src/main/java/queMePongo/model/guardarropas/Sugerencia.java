package queMePongo.model.guardarropas;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.Calificacion;
import queMePongo.model.usuaries.Usuarie;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
public class Sugerencia {
	
	@GeneratedValue
	@Id
	private long id;
	
	@ManyToOne
	@Cascade(CascadeType.PERSIST)
	private Usuarie usuarie;
	@OneToOne
	@Cascade(CascadeType.ALL)
	private Atuendo atuendo;
	private String textoCalificacion = "Sin Calificar";
	private String textoBoton = "Calificar";
	
	public Sugerencia(Usuarie usuarie, Atuendo atuendo) {
		setUsuarie(usuarie);
		setAtuendo(atuendo);
	}
	
	public void calificar(Calificacion calificacion){
		this.textoCalificacion = "Calificado como: " + calificacion.toString();
		this.textoBoton = "Cambiar Calificacion";
	}
	
}
