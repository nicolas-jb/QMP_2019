package queMePongo.spark.wrappers.calendario;

import lombok.AllArgsConstructor;
import lombok.Data;
import queMePongo.model.usuaries.Evento;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaCalendarioEventosWrapper {
	private Integer dia;
	private List<Evento> eventos;
}
