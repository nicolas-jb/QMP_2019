package queMePongo.model.guardarropas;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Test;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.Calificacion;
import queMePongo.model.excepciones.FaltanPrendasException;
import queMePongo.model.prendas.BorradorPrenda;
import queMePongo.model.prendas.Categoria;
import queMePongo.model.prendas.Prenda;
import queMePongo.model.usuaries.Usuarie;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GeneradorDeSugerenciasTest {
	
	private Prenda remera = BorradorPrenda.crearRemeraRandom();
	private Prenda pantalon = BorradorPrenda.crearPantalonRandom();
	private Prenda zapato = BorradorPrenda.crearZapatoRandom();
	private Prenda sombrero = BorradorPrenda.crearAccesorioRandom();
	private Prenda buzo = BorradorPrenda.crearBuzoRandom();
	private Prenda campera = BorradorPrenda.crearCamperaRandom();
	private List<Prenda> prendas = Arrays.asList(zapato, pantalon, remera, sombrero, buzo, campera);
	private List<Prenda> prendasSinBase = Arrays.asList(zapato, pantalon, sombrero, buzo, campera);
	private Atuendo atuendoBase;
	private Atuendo atuendoBaseConAccesorio;
	private Atuendo atuendoIntermedio;
	private Atuendo atuendoIntermedioConAccesorio;
	private Atuendo atuendoCompleto;
	private Atuendo atuendoCompletoConAccesorio;
	private Usuarie usuarie;
	private LocalDate fechaTest;
	private Guardarropa guardarropaTest;
	
	@Before
	public void crearAtuendos() {
		atuendoBase = new Atuendo(remera, pantalon, zapato);
		atuendoBaseConAccesorio = new Atuendo(remera, pantalon, zapato);
		atuendoBaseConAccesorio.agregarAccesorio(sombrero);
		
		atuendoIntermedio = new Atuendo(remera, pantalon, zapato, buzo);
		atuendoIntermedioConAccesorio = new Atuendo(remera, pantalon, zapato, buzo);
		atuendoIntermedioConAccesorio.agregarAccesorio(sombrero);
		
		atuendoCompleto = new Atuendo(remera, pantalon, zapato, buzo, campera);
		atuendoCompletoConAccesorio = new Atuendo(remera, pantalon, zapato, buzo, campera);
		atuendoCompletoConAccesorio.agregarAccesorio(sombrero);
		
		guardarropaTest = new Guardarropa(Capacidad.ILIMITADA);
		usuarie = new Usuarie("Franco");
		fechaTest = LocalDate.now();
	}
	
	@Test
	public void filtrarPrendasClimaMuyCaluroso() {
		List<Prenda> prendasFiltradas = GeneradorDeSugerencias.getInstance().filtrarPrendasAcordeAlClima(45.0, prendas);
		assertEquals(2, prendasFiltradas.size());
		assertFalse(prendasFiltradas.contains(campera));
		assertFalse(prendasFiltradas.contains(buzo));
		assertFalse(prendasFiltradas.contains(sombrero));
		assertFalse(prendasFiltradas.contains(pantalon));
	}
	
	@Test
	public void filtrarPrendasClimaTemplado() {
		List<Prenda> prendasFiltradas = GeneradorDeSugerencias.getInstance().filtrarPrendasAcordeAlClima(18.0, prendas);
		assertEquals(5, prendasFiltradas.size());
		assertFalse(prendasFiltradas.contains(campera));
	}
	
	@Test
	public void filtrarPrendasClimaMuyFrio() {
		List<Prenda> prendasFiltradas = GeneradorDeSugerencias.getInstance().filtrarPrendasAcordeAlClima(-10.0, prendas);
		assertEquals(5, prendasFiltradas.size());
		assertFalse(prendasFiltradas.contains(sombrero));
	}
	
	@Test
	public void generarCantidadCorrectaDeSugerencia() {
		List<Sugerencia> sugerencias;
		
		sugerencias = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(-10.0, prendas, usuarie);
		assertEquals(1, sugerencias.size());
		
		sugerencias = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(0.0, prendas, usuarie);
		assertEquals(2, sugerencias.size());
		
		sugerencias = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(10.0, prendas, usuarie);
		assertEquals(2, sugerencias.size());
		
		sugerencias = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(20.0, prendas, usuarie);
		assertEquals(2, sugerencias.size());
		
		sugerencias = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(30.0, prendas, usuarie);
		assertEquals(1, sugerencias.size());
	}
	
	@Test(expected = FaltanPrendasException.class)
	public void faltaPrendaBase() {
		GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(20.0, prendasSinBase, usuarie);
	}
	
	@Test
	public void atuendosSugeridosBienArmados() {
		List<Sugerencia> sugerencias = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(10.0, prendas, usuarie);
		assertTrue(sugerencias.stream().anyMatch(sugerencia -> mismoAtuendo(sugerencia.getAtuendo(), atuendoCompleto)));
		assertTrue(sugerencias.stream().anyMatch(sugerencia -> mismoAtuendo(sugerencia.getAtuendo(), atuendoCompletoConAccesorio)));
	}
	
	@Test
	public void atuendosSugeridosMalArmados() {
		List<Sugerencia> atuendosSugeridos = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(10.0, prendas, usuarie);
		
		assertFalse(atuendosSugeridos.stream().anyMatch(sugerencia -> mismoAtuendo(sugerencia.getAtuendo(), atuendoBase)));
		assertFalse(atuendosSugeridos.stream().anyMatch(sugerencia -> mismoAtuendo(sugerencia.getAtuendo(), atuendoBaseConAccesorio)));
		assertFalse(atuendosSugeridos.stream().anyMatch(sugerencia -> mismoAtuendo(sugerencia.getAtuendo(), atuendoIntermedio)));
		assertFalse(atuendosSugeridos.stream().anyMatch(sugerencia -> mismoAtuendo(sugerencia.getAtuendo(), atuendoIntermedioConAccesorio)));
		
	}
	
	
	@Test
	public void laSugerenciaCambiaSegunSensibilidad() {
		Usuarie usuarie1 = new Usuarie("Nico");
		
		List<Sugerencia> sugerencias1 = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(13D, prendas, usuarie1);
		
		usuarie1.aceptar(atuendoBase, guardarropaTest, fechaTest);
		for (int i = 0; i < 10; i += 1) {
			usuarie1.calificar(atuendoBase, Calificacion.MUCHO_FRIO, new ImmutablePair<>(Categoria.PARTE_SUPERIOR, Calificacion.MUCHO_FRIO));
		}
		
		List<Sugerencia> sugerencias2 = GeneradorDeSugerencias.getInstance().generarSugerenciaSegunLaTemperatura(13D, prendas, usuarie1);
		
		sugerencias1.forEach(sugerencia ->
				assertEquals(2, sugerencia.getAtuendo().getPrendasSuperiores().size()));
		sugerencias2.forEach(sugerencia ->
				assertEquals(3, sugerencia.getAtuendo().getPrendasSuperiores().size()));
	}
	
	
	private boolean mismoAtuendo(Atuendo atuendo1, Atuendo atuendo2) {
		return atuendo1.getPrendaInferior().getTipoPrenda() == atuendo2.getPrendaInferior().getTipoPrenda() &&
				atuendo1.getCalzado().getTipoPrenda() == atuendo2.getCalzado().getTipoPrenda() &&
				atuendo1.getPrendasSuperiores().stream().allMatch(
						prenda1 -> atuendo2.getPrendasSuperiores().stream().anyMatch(
								prenda2 -> prenda2.getTipoPrenda() == prenda1.getTipoPrenda()));
	}
	
}
