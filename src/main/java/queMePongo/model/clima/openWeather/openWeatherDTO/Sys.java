package queMePongo.model.clima.openWeather.openWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sys {
	private int type;
	private int id;
	private float message;
	private String country;
	private long sunrise;
	private long sunset;
}
