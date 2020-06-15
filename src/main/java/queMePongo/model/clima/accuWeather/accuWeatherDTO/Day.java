package queMePongo.model.clima.accuWeather.accuWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Day {
	private int icon;
	private String iconPhrase;
	private boolean hasPrecipitation;
}
