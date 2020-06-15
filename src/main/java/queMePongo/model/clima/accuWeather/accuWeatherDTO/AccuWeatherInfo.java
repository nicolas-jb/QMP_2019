package queMePongo.model.clima.accuWeather.accuWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AccuWeatherInfo {
	private Headline headline;
	private List<DailyForecasts> dailyForecasts;
	
	public double getTemperatura() {
		return dailyForecasts.get(0).getTemperature().temperaturaReal();
	}
}

