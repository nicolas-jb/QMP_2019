package queMePongo.spark.wrappers.sugerencias;

import lombok.AllArgsConstructor;
import lombok.Data;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.guardarropas.Sugerencia;
import queMePongo.model.usuaries.Evento;

import java.util.List;

@Data
@AllArgsConstructor
public class EventoSugerenciasWrapper {
	private Evento evento;
	private List<Sugerencia> sugerencias;
}
