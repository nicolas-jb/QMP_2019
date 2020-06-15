package queMePongo.model.clima;

import org.junit.Before;
import org.junit.Test;
import queMePongo.model.utils.ConversorDeTemperaturas;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProveedorDeClimaTest {
	
	private SistemaDeClima sistemaDeClima;
	private InfoClima climaTemplado;
	private InfoClima climaFrio;
	private InfoClima climaCaluroso;
	
	@Before
	public void mockeaUnSistemaDeClimaQueDa20Grados() {
		sistemaDeClima = mock(SistemaDeClima.class);
		climaTemplado = new InfoClima(20D);
		climaCaluroso = new InfoClima(35D);
		climaFrio = new InfoClima(5D);
	}
	
	@Test
	public void conversionDeKelvinACelcius() {
		Double kelvin = 293.15D;
		assertEquals(20, ConversorDeTemperaturas.deKelvinACelsius(kelvin), 0.00001);
	}
	
	@Test
	public void conversionDeFarenheitACelcius() {
		Double fahrenheit = 68.0D;
		assertEquals(20, ConversorDeTemperaturas.deFahrenheitACelsius(fahrenheit), 0.00001);
	}
	
	@Test
	public void obtenerInfoClima() {
		ProveedorDeClima proveedorDeClima = ProveedorDeClima.getInstance();
		when(sistemaDeClima.obtenerInfoClima()).thenReturn(climaTemplado);
		proveedorDeClima.setSistemaDeClima(sistemaDeClima);
		proveedorDeClima.actualizarInfoClima();
		
		assertEquals(climaTemplado, proveedorDeClima.obtenerInfoClima());
	}
	
	@Test
	public void actualizarInfoClima() {
		ProveedorDeClima proveedorDeClima = ProveedorDeClima.getInstance();
		when(sistemaDeClima.obtenerInfoClima()).thenReturn(climaTemplado);
		proveedorDeClima.setSistemaDeClima(sistemaDeClima);
		proveedorDeClima.actualizarInfoClima();
		
		assertEquals(climaTemplado, proveedorDeClima.obtenerInfoClima());
		
		when(sistemaDeClima.obtenerInfoClima()).thenReturn(climaCaluroso);
		proveedorDeClima.actualizarInfoClima();
		
		assertEquals(climaCaluroso, proveedorDeClima.obtenerInfoClima());
	}
	
	@Test
	public void obtenerTemperatura() {
		ProveedorDeClima proveedorDeClima = ProveedorDeClima.getInstance();
		when(sistemaDeClima.obtenerInfoClima()).thenReturn(climaFrio);
		proveedorDeClima.setSistemaDeClima(sistemaDeClima);
		proveedorDeClima.actualizarInfoClima();
		assertEquals(5D, proveedorDeClima.obtenerTemperatura(), 0.0001);
	}
	
	@Test
	public void cambiarSistemaDeClima() {
		ProveedorDeClima proveedorDeClima = ProveedorDeClima.getInstance();
		SistemaDeClima openWeather = mock(SistemaDeClima.class);
		SistemaDeClima accuWeather = mock(SistemaDeClima.class);
		when(openWeather.obtenerInfoClima()).thenReturn(climaCaluroso);
		when(accuWeather.obtenerInfoClima()).thenReturn(climaFrio);
		
		proveedorDeClima.setSistemaDeClima(openWeather);
		proveedorDeClima.actualizarInfoClima();
		assertEquals(35D, proveedorDeClima.obtenerTemperatura(), 0.0001);
		
		proveedorDeClima.setSistemaDeClima(accuWeather);
		proveedorDeClima.actualizarInfoClima();
		assertEquals(5D, proveedorDeClima.obtenerTemperatura(), 0.0001);
	}
}
