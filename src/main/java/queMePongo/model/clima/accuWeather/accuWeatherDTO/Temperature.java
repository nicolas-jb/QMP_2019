package queMePongo.model.clima.accuWeather.accuWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import queMePongo.model.utils.ConversorDeTemperaturas;

@Data
@NoArgsConstructor
public class Temperature {
	private TemperatureInfo minimum;
	private TemperatureInfo maximum;
	
	
	public double temperaturaReal() {
		return this.calcularPromedioTemperaturas();
	}
	
	public double calcularPromedioTemperaturas() {
		double min = this.minimum.getValue();
		double max = this.maximum.getValue();
		double promedio = (min + max) / 2;
		return ConversorDeTemperaturas.deFahrenheitACelsius(promedio); //pasado de farenheit a celsius
	}
}
