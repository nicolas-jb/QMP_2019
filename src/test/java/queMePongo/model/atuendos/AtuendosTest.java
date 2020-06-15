package queMePongo.model.atuendos;

	import org.junit.Before;
	import org.junit.Test;
import queMePongo.model.excepciones.CapaNoPermitidaException;
import queMePongo.model.excepciones.CategoriaNoPermitidaException;
import queMePongo.model.excepciones.EstadoNoValidoParaCalificarException;
import queMePongo.model.prendas.BorradorPrenda;
import queMePongo.model.prendas.Prenda;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static queMePongo.model.atuendos.Estado.ACEPTADO;
import static queMePongo.model.atuendos.Estado.NUEVO;

public class AtuendosTest {
	
	private Atuendo atuendoBasico;
	private Atuendo atuendoConBuzo;
	private Atuendo atuendoConBuzoYCampera;
	private Prenda remera = BorradorPrenda.crearRemeraRandom();
	private Prenda pantalon = BorradorPrenda.crearPantalonRandom();
	private Prenda zapato = BorradorPrenda.crearZapatoRandom();
	private Prenda sombrero = BorradorPrenda.crearAccesorioRandom();
	private Prenda buzo = BorradorPrenda.crearBuzoRandom();
	private Prenda campera = BorradorPrenda.crearCamperaRandom();
	
	@Before
	public void crearAtuendos() {
		atuendoBasico = new Atuendo(remera, pantalon, zapato);
		atuendoConBuzo = new Atuendo(remera, pantalon, zapato, buzo);
		atuendoConBuzoYCampera = new Atuendo(remera, pantalon, zapato, buzo, campera);
	}
	
	@Test(expected = CategoriaNoPermitidaException.class)
	public void agregarAccesorioValidoEInvalido() {
		atuendoBasico.agregarAccesorio(sombrero);
		assertEquals(atuendoBasico.getAccesorio(), Optional.of(sombrero));
		
		atuendoConBuzo.agregarAccesorio(remera);
	}
	
	@Test(expected = CapaNoPermitidaException.class)
	public void constructorAtuendo_capaBase() {
		new Atuendo(buzo, pantalon, zapato);
	}
	
	@Test(expected = CapaNoPermitidaException.class)
	public void constructorAtuendo_capaIntermedia() {
		new Atuendo(remera, pantalon, zapato, remera);
	}
	
	@Test(expected = CapaNoPermitidaException.class)
	public void constructorAtuendo_capaFinal() {
		new Atuendo(remera, pantalon, zapato, buzo, buzo);
	}
	
	@Test
	public void transicionDeEstados() {
		// Recien creado debe ser Nuevo
		assertEquals(NUEVO, atuendoConBuzoYCampera.getEstado());
		
		atuendoConBuzoYCampera.cambiarEstado(ACEPTADO);
		assertEquals(ACEPTADO, atuendoConBuzoYCampera.getEstado());
	}
	
	@Test(expected = EstadoNoValidoParaCalificarException.class)
	public void calificarAtuendoNoAceptado() {
		atuendoBasico.calificar(Calificacion.MUCHO_CALOR);
	}
	
	@Test
	public void calificarAtuendoAceptado() {
		atuendoBasico.cambiarEstado(ACEPTADO);
		atuendoBasico.calificar(Calificacion.CALOR);
		assertEquals(Calificacion.CALOR, atuendoBasico.getCalificacion());
	}
	
	@Test
	public void getterPrendaSuperiorRetornaCapaBase() {
		assertEquals(remera, atuendoConBuzoYCampera.getPrendaSuperior());
	}
}
