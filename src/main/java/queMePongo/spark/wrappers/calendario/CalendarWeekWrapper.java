package queMePongo.spark.wrappers.calendario;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CalendarWeekWrapper {
	List<DiaCalendarioEventosWrapper> dias;
}
