package queMePongo.model.clima.openWeather.openWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Weather {
	private int id;
	private String main;
	private String description;
	private String icon;
	
}
