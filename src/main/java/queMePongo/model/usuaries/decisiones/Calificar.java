package queMePongo.model.usuaries.decisiones;

import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.Calificacion;

public class Calificar extends Decision {
	
	private Calificacion calificacionAnterior;
	
	public Calificar(Atuendo atuendo) {
		super(atuendo);
		this.calificacionAnterior = atuendo.getCalificacion();
	}
	
	@Override
	public void deshacer() {
		if (calificacionAnterior != null) {
			atuendo.calificar(calificacionAnterior);
		} else {
			super.deshacer();
		}
	}
	
	
}

