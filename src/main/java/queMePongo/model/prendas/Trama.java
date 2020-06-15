package queMePongo.model.prendas;

import org.apache.commons.lang.StringUtils;

public enum Trama {
	LISA,
	RAYADA,
	LUNARES,
	CUADROS,
	ESAMPADO;

	public String getNombre() {
		return StringUtils.capitalize(this.toString().toLowerCase());
	}
}
