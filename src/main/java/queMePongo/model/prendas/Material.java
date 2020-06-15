package queMePongo.model.prendas;

import org.apache.commons.lang.StringUtils;

public enum Material {
	JEAN,
	ALGODON,
	LYCRA,
	CUERO,
	NYLON,
	LANA,
	CORDEROY;

	public String getNombre() {
		return StringUtils.capitalize(this.toString().toLowerCase());
	}
}
