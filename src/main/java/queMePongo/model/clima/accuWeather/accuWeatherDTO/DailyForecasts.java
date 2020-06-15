package queMePongo.model.clima.accuWeather.accuWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DailyForecasts {
	private String date;
	private long epochDate;
	private Temperature temperature;
	private Day day;
	private Day night;
	private List<String> sources;
	private String mobileLink;
	private String link;
}



