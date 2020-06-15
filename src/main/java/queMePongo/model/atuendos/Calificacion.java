package queMePongo.model.atuendos;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Calificacion {
	MUCHO_CALOR(2),
	CALOR(1),
	OK(0),
	FRIO(-1),
	MUCHO_FRIO(-2);
	
	private Integer valor;
	private static final double COEFICIENTE_DE_SENSIBILIDAD = 0.5; // La medida en la cual se modifica la sensibilidad
	
	Calificacion(Integer valor) {
		this.valor = valor;
	}
	
	public double getCoeficiente() {
		return valor * COEFICIENTE_DE_SENSIBILIDAD;
	}
	
	public static Calificacion getCalificacion(Integer val){
		return Arrays.stream(Calificacion.values()).filter(calificacion -> calificacion.valor.equals(val)).collect(Collectors.toList()).get(0);
	}
	
}
