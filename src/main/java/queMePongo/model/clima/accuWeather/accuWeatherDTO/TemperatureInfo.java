package queMePongo.model.clima.accuWeather.accuWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TemperatureInfo {
	private double value;
	private String unit;
	private int unitType;
}
