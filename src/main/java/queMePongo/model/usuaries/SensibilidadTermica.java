package queMePongo.model.usuaries;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import queMePongo.model.atuendos.Calificacion;
import queMePongo.model.prendas.Categoria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@NoArgsConstructor
public class SensibilidadTermica {
	
	@GeneratedValue
	@Id
	private long id;
	
	@org.hibernate.annotations.Type(
			type = "org.hibernate.type.SerializableToBlobType",
			parameters = {@org.hibernate.annotations.Parameter(name = "classname", value = "java.util.HashMap")})
	private Map<Categoria, Double> preferencias = inicializarPreferencias();
	
	public Map<Categoria, Double> getPreferencias() {
		return preferencias;
	}
	
	public void sumarSensibilidad(Categoria categoria, Double diferencia) {
		Double viejoValor = obtenerDiferenciaDeTemperatura(categoria);
		preferencias.put(categoria, viejoValor + diferencia);
	}
	
	public void agregarCalificacion(Pair<Categoria, Calificacion> calificacionPorPrenda) {
		sumarSensibilidad(calificacionPorPrenda.getLeft(), calificacionPorPrenda.getRight().getCoeficiente());
	}
	
	public Double obtenerSensacionTermica(Categoria categoria, Double temperatura) {
		return obtenerDiferenciaDeTemperatura(categoria) + temperatura;
	}
	
	public Double obtenerDiferenciaDeTemperatura(Categoria categoria) {
		return preferencias.getOrDefault(categoria, 0D);
	}
	
	private Map<Categoria, Double> inicializarPreferencias() {
		return Stream.of(Categoria.values())
				.map(categoria -> new AbstractMap.SimpleEntry<>(categoria, 0D))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
