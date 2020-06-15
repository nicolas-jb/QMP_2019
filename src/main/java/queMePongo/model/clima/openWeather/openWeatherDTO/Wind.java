package queMePongo.model.clima.openWeather.openWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Wind {
	private float speed;
	private int deg;
}
