package queMePongo.model.clima.openWeather;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import queMePongo.model.clima.InfoClima;
import queMePongo.model.clima.SistemaDeClima;
import queMePongo.model.clima.openWeather.openWeatherDTO.OpenWeatherInfo;
import queMePongo.model.utils.Connector;

@Data
public class OpenWeather implements SistemaDeClima {
	
	private static OpenWeather ourInstance = new OpenWeather();
	
	public static OpenWeather getInstance() {
		return ourInstance;
	}
	
	private String idCiudad = "3435910"; // BUENOS AIRES PAPAAAAAAAAA
	private String apiKey = "ef2418d539795a854d3fae31878b2697";
	private String url = "https://api.openweathermap.org/data/2.5/weather?id=" + idCiudad + "&appid=" + apiKey;
	
	public InfoClima obtenerInfoClima() throws IllegalArgumentException {
		System.out.println("Estoy obteniendo el clima");
		String json = Connector.get(url);
		OpenWeatherInfo unMeteorologo = new Gson().fromJson(json, OpenWeatherInfo.class);
		return OpenWeatherDtoTransformer.transform(unMeteorologo);
	}
}
