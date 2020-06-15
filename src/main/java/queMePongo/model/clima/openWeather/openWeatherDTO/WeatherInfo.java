package queMePongo.model.clima.openWeather.openWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherInfo {
	private Double temp;
	private Integer pressure;
	private Integer humidity;
	private Double temp_min;
	private Double temp_max;
}
