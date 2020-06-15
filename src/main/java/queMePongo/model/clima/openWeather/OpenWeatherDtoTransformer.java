package queMePongo.model.clima.openWeather;

import queMePongo.model.clima.InfoClima;
import queMePongo.model.clima.openWeather.openWeatherDTO.OpenWeatherInfo;
import queMePongo.model.utils.ConversorDeTemperaturas;

public class OpenWeatherDtoTransformer {
	
	public static InfoClima transform(OpenWeatherInfo info) {
		return new InfoClima(
				ConversorDeTemperaturas.deKelvinACelsius(info.getMain().getTemp())
		);
	}
}
