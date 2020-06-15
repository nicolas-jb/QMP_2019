package queMePongo.model.prendas;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class NivelDeAbrigo {
	private int maximo;
	private int minimo;
	
	public NivelDeAbrigo(int minimo, int maximo) {
		this.minimo = minimo;
		this.maximo = maximo;
	}
	
	public NivelDeAbrigo() {
	}
	
	public boolean cubre(Double temperatura) {
		return temperatura >= minimo && temperatura <= maximo;
	}
}
