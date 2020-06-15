package queMePongo.model.clima.accuWeather;

import com.google.gson.Gson;
import queMePongo.model.clima.InfoClima;
import queMePongo.model.clima.SistemaDeClima;
import queMePongo.model.clima.accuWeather.accuWeatherDTO.AccuWeatherInfo;
import queMePongo.model.utils.Connector;

public class AccuWeather implements SistemaDeClima {
	
	private static AccuWeather ourInstance = new AccuWeather();
	
	public static AccuWeather getInstance() {
		return ourInstance;
	}
	
	private String idCiudad = "7894";
	private String apiKey = "o31sWionig8LnrUN3GBSeid9jeg23wn9";
	private String url = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + idCiudad + "?apikey=" + apiKey;
	
	public InfoClima obtenerInfoClima() throws IllegalArgumentException {
		System.out.println("Estoy obteniendo el clima");
		String json = Connector.get(url);
		AccuWeatherInfo unMeteorologo = new Gson().fromJson(json, AccuWeatherInfo.class);
		return new InfoClima(unMeteorologo.getTemperatura());
	}
}
