package queMePongo.model.clima.openWeather.openWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenWeatherInfo {
	private List<Weather> weather = new ArrayList<>();
	private String base;
	private WeatherInfo main;
	private int visibility;
	private Wind wind;
	private Clouds clouds;
	private int dt;
	private Sys sys;
	private long id;
	private String name;
	private int cod;
	private Coordinates coord;
}
