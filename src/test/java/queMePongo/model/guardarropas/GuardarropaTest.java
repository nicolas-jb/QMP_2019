package queMePongo.model.guardarropas;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.excepciones.GuardarropaLlenoException;
import queMePongo.model.prendas.BorradorPrenda;
import queMePongo.model.prendas.Prenda;
import queMePongo.model.usuaries.Usuarie;

import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GuardarropaTest {
	
	private Prenda remera = BorradorPrenda.crearRemeraRandom();
	private Prenda pantalon = BorradorPrenda.crearPantalonRandom();
	private Prenda zapato = BorradorPrenda.crearZapatoRandom();
	private Atuendo atuendoBase;
	private Usuarie usuarie;
	private LocalDate fechaTest;
	private Guardarropa guardarropaTest;
	
	@Before
	public void prepararTest() {
		atuendoBase = new Atuendo(remera, pantalon, zapato);
		guardarropaTest = new Guardarropa(Capacidad.LIMITADA);
		usuarie = new Usuarie("Franco");
		fechaTest = LocalDate.now();
	}
	
	@Test
	public void capacidadLimitadaPorDefault() {
		Guardarropa guardarropa = new Guardarropa();
		assertEquals(Capacidad.LIMITADA, guardarropa.getCapacidad());
	}
	
	@Test
	public void crearConCapacidadIlimitada() {
		Guardarropa guardarropa = new Guardarropa(Capacidad.ILIMITADA);
		assertEquals(Capacidad.ILIMITADA, guardarropa.getCapacidad());
	}
	
	@Test
	public void agregarPrendaIncrementaLaCantidadDePrendas() {
		Guardarropa guardarropa = new Guardarropa();
		Prenda remera = BorradorPrenda.crearRemeraRandom();
		
		assertEquals(0, guardarropa.getCantidadDePrendas());
		
		guardarropa.agregarPrenda(remera);
		
		assertEquals(1, guardarropa.getCantidadDePrendas());
	}
	
	@Test
	public void contieneLaPrendaAgregada() {
		Guardarropa guardarropa = new Guardarropa();
		Prenda remera = BorradorPrenda.crearRemeraRandom();
		guardarropa.agregarPrenda(remera);
		
		assertTrue(guardarropa.getPrendas().contains(remera));
	}
	
	@Test(expected = GuardarropaLlenoException.class)
	public void excederLaCapacidadLanzaExcepcion() {
		Guardarropa guardarropa = new Guardarropa(); // El limite de logPage es 20.
		Prenda remera = BorradorPrenda.crearRemeraRandom();
		for (int i = 0; i < 21; i++) guardarropa.agregarPrenda(remera); // Intentamos agregar 21 remeras
	}
	
	@Test
	public void capacidadIlimitadaNoTieneLimite() {
		Capacidad premium = Capacidad.ILIMITADA;
		Integer valorMaximoDeUnInteger = new Integer(2147483647); // 32 bits
		Assert.assertTrue(premium.puedeAgregarPrenda(valorMaximoDeUnInteger));
	}
	
//	@Test
//	public void laReservaSeRealizoPrenda() {
//		guardarropaTest.agregarPrenda(remera);
//		guardarropaTest.agregarPrenda(pantalon);
//		guardarropaTest.agregarPrenda(zapato);
//
//		guardarropaTest.getReservador().reservar(fechaTest, remera);
//		guardarropaTest.getReservador().reservar(fechaTest, pantalon);
//
//		assertFalse(guardarropaTest.getPrendasDisponiblesParaUnaFecha(fechaTest).contains(remera));
//		assertFalse(guardarropaTest.getPrendasDisponiblesParaUnaFecha(fechaTest).contains(pantalon));
//	}
//
//	@Test
//	public void laReservaSeRealizoAtuendo() {
//		guardarropaTest.agregarPrenda(remera);
//		guardarropaTest.agregarPrenda(pantalon);
//		guardarropaTest.agregarPrenda(zapato);
//
//		usuarie.aceptar(atuendoBase, guardarropaTest, fechaTest);
//
//		assertFalse(guardarropaTest.getPrendasDisponiblesParaUnaFecha(fechaTest).contains(remera));
//		assertFalse(guardarropaTest.getPrendasDisponiblesParaUnaFecha(fechaTest).contains(pantalon));
//	}
	
	
}
