package queMePongo.model.clima;

import queMePongo.model.clima.accuWeather.AccuWeather;
import queMePongo.model.clima.openWeather.OpenWeather;
import queMePongo.model.excepciones.NoSePudoActualizarElClimaException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProveedorDeClima {
	
	private List<SistemaDeClima> sistemasDeClima = Arrays.asList(OpenWeather.getInstance(), AccuWeather.getInstance());
	private InfoClima infoClima;
	
	private ProveedorDeClima() { }
	
	private static ProveedorDeClima ourInstance = new ProveedorDeClima();
	
	public static ProveedorDeClima getInstance() {
		return ourInstance;
	}
	
	public void setSistemaDeClima(SistemaDeClima sistemaDeClima) {
		this.sistemasDeClima = Collections.singletonList(sistemaDeClima);
	}
	
	public InfoClima obtenerInfoClima() {
		return infoClima != null ? infoClima : actualizarInfoClima();
	}
	
	public InfoClima actualizarInfoClima() {
		for (SistemaDeClima sistemaDeClima : sistemasDeClima) {
			try{
				infoClima = sistemaDeClima.obtenerInfoClima();
				return infoClima;
			} catch (Exception e) {}
		}
		throw new NoSePudoActualizarElClimaException();
	}
	
	public Double obtenerTemperatura() {
		return obtenerInfoClima().getTemperatura();
	}
	
}
