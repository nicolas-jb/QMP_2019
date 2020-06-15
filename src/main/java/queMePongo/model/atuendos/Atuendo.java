package queMePongo.model.atuendos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import queMePongo.model.excepciones.CapaNoPermitidaException;
import queMePongo.model.excepciones.CategoriaNoPermitidaException;
import queMePongo.model.excepciones.EstadoNoValidoParaCalificarException;
import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.guardarropas.Reservador;
import queMePongo.model.prendas.Capa;
import queMePongo.model.prendas.Categoria;
import queMePongo.model.prendas.Prenda;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Data
public class Atuendo {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private List<Prenda> prendasSuperiores = new ArrayList<>();
	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private Prenda prendaInferior;
	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private Prenda calzado;
	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private Prenda accesorio;
	@Enumerated(value = EnumType.STRING)
	private Estado estado = Estado.NUEVO;
	@Enumerated(value = EnumType.STRING)
	private Calificacion calificacion;
	
	public Atuendo(Prenda prendaBase, Prenda prendaInferior, Prenda calzado) {
		validarPrendaEsDeCapa(prendaBase, Capa.BASE);
		this.prendasSuperiores.add(prendaBase);
		this.prendaInferior = prendaInferior;
		this.calzado = calzado;
	}
	
	public Atuendo(Prenda prendaBase, Prenda prendaInferior, Prenda calzado, Prenda unAbrigo) {
		this(prendaBase, prendaInferior, calzado);
		
		validarPrendaEsDeCapa(unAbrigo, Capa.INTERMEDIA);
		this.prendasSuperiores.add(unAbrigo);
	}
	
	public Atuendo(Prenda prendaBase, Prenda prendaInferior, Prenda calzado, Prenda unAbrigo, Prenda otroAbrigo) {
		this(prendaBase, prendaInferior, calzado, unAbrigo);
		
		validarPrendaEsDeCapa(otroAbrigo, Capa.FINAL);
		this.prendasSuperiores.add(otroAbrigo);
	}
	
	private void validarPrendaEsDeCapa(Prenda prenda, Capa capa) {
		if (!prenda.getCapa().equals(capa)) throw new CapaNoPermitidaException();
	}
	
	public void agregarAccesorio(Prenda accesorio) {
		if (!accesorio.getCategoria().equals(Categoria.ACCESORIOS)) {
			throw new CategoriaNoPermitidaException();
		}
		
		this.accesorio = accesorio;
	}
	
	public Prenda getPrendaSuperior() {
		return prendasSuperiores.get(0);
	}
	
	public Optional<Prenda> getAccesorio() {
		return Optional.ofNullable(accesorio);
	}
	
	public void cambiarEstado(Estado nuevoEstado) {
		this.estado = nuevoEstado;
		//habría que ver que esto no pueda pasar de por ejemplo rechazado a aceptado?
	}
	
	public void calificar(Calificacion unaCalificacion) {
		if (!estado.permiteCalificar()) {
			throw new EstadoNoValidoParaCalificarException();
		}
		this.calificacion = unaCalificacion;
		this.cambiarEstado(Estado.CALIFICADO);
	}
	
	public void reservarPrendas(Guardarropa guardarropa, LocalDate fecha) { //no tenia de donde reservar sino se llama cuando el usuario acepta el atuendo
		
		Reservador reservador = guardarropa.getReservador();
		List<Prenda> prendasAReservar = prendasSuperiores;
		
		//Arrays.asList(prendaInferior, calzado); no funcaba si lo declaraba asi
		prendasAReservar.add(prendaInferior);
		prendasAReservar.add(calzado);
		
		if (accesorio != null) {
			prendasAReservar.add(accesorio);
		}
		prendasAReservar.forEach(prenda -> reservador.reservar(fecha, prenda));
	}
	
	public void agregarAbrigo(Prenda unAbrigo){
		this.prendasSuperiores.add(unAbrigo);
	}
}
