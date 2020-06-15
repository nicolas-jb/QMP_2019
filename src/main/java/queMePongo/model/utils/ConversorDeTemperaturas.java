package queMePongo.model.utils;

public class ConversorDeTemperaturas {
	
	public static Double deKelvinACelsius(Double temp) {
		return temp - 273.15;
	}
	
	public static Double deFahrenheitACelsius(Double temp) {
		return ((temp - 32) * 5) / 9;
	}
}
