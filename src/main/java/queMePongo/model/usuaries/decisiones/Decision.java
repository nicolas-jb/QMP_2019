package queMePongo.model.usuaries.decisiones;

import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.Estado;

public abstract class Decision {
	protected Atuendo atuendo;
	protected Estado estadoAnterior;
	
	public Decision(Atuendo atuendo) {
		this.atuendo = atuendo;
		this.estadoAnterior = atuendo.getEstado();
	}
	
	public void deshacer() {
		atuendo.cambiarEstado(estadoAnterior);
	}
}
