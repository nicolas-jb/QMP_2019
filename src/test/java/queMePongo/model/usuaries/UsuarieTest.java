package queMePongo.model.usuaries;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.Calificacion;
import queMePongo.model.excepciones.EstadoNoValidoParaCalificarException;
import queMePongo.model.excepciones.SuscripcionNoPermiteAgregarGuardarropaException;
import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.guardarropas.Capacidad;
import queMePongo.model.prendas.BorradorPrenda;
import queMePongo.model.prendas.Prenda;

import java.time.LocalDate;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static queMePongo.model.atuendos.Calificacion.*;
import static queMePongo.model.atuendos.Estado.*;
import static queMePongo.model.prendas.Categoria.*;

public class UsuarieTest {
	
	private Usuarie usuarieNormal;
	private Usuarie usuariePremium;
	private Atuendo atuendo;
	private Evento irATrabajar;
	private Guardarropa guardarropaIlimitado;
	private Guardarropa guardarropaLimitado;
	private LocalDate fechaTest;
	
	
	@Before
	public void crearUsuaries() {
		usuarieNormal = new Usuarie("Tom");
		usuariePremium = new Usuarie("Mora", Suscripcion.PREMIUM);
		atuendo = new Atuendo(BorradorPrenda.crearRemeraRandom(), BorradorPrenda.crearPantalonRandom(), BorradorPrenda.crearZapatoRandom());
		guardarropaIlimitado = new Guardarropa(Capacidad.ILIMITADA);
		guardarropaLimitado = new Guardarropa(Capacidad.LIMITADA);
		fechaTest = LocalDate.now();
		irATrabajar = new Evento(LocalDate.now(), "Ir a trabajar");
	}
	
	@Test
	public void usuarioDefaultSuscripcionGratuita() {
		Assert.assertEquals(Suscripcion.GRATUITA,usuarieNormal.getSuscripcion());
	}
	
	@Test
	public void usuarioPremiumSuscripcionPremium() {
		Assert.assertEquals(Suscripcion.PREMIUM, usuariePremium.getSuscripcion());
	}
	
	@Test(expected = SuscripcionNoPermiteAgregarGuardarropaException.class)
	public void usuarioGratuitoNoPermiteGuardarropaIlimitado() {
		usuarieNormal.agregarGuardarropa(guardarropaIlimitado);
	}
	
	@Test
	public void laListaDeDecisionesIncrementa() {
		usuarieNormal.aceptar(atuendo, guardarropaLimitado, fechaTest);
		usuarieNormal.rechazar(atuendo);
		
		int cantidadDeDecisiones = usuarieNormal.getDecisiones().size();
		Assert.assertEquals(2, cantidadDeDecisiones);
	}
	
	@Test
	public void alTomarDecisionesCambiaElEstado() {
		Assert.assertEquals(NUEVO, atuendo.getEstado());
		usuarieNormal.aceptar(atuendo, guardarropaLimitado, fechaTest);
		Assert.assertEquals(ACEPTADO, atuendo.getEstado());
		usuarieNormal.rechazar(atuendo);
		Assert.assertEquals(RECHAZADO, atuendo.getEstado());
	}
	
	@Test
	public void calificarAtuendoAceptado() {
		usuarieNormal.aceptar(atuendo, guardarropaLimitado, fechaTest);
		
		usuarieNormal.calificar(atuendo, MUCHO_CALOR);
		Assert.assertEquals(MUCHO_CALOR, atuendo.getCalificacion());
		
		// Recalificar
		usuarieNormal.calificar(atuendo, CALOR);
		Assert.assertEquals(CALOR, atuendo.getCalificacion());
	}
	
	@Test(expected = EstadoNoValidoParaCalificarException.class)
	public void calificarAtuendoNuevo() {
		usuarieNormal.calificar(atuendo, OK);
		Assert.assertNull(atuendo.getCalificacion());
	}
	
	@Test
	public void deshacerDesiciones() {
		usuariePremium.aceptar(atuendo, guardarropaIlimitado, fechaTest);
		usuariePremium.calificar(atuendo, Calificacion.MUCHO_FRIO);
		usuariePremium.calificar(atuendo, Calificacion.FRIO);
		Assert.assertEquals(3, usuariePremium.getDecisiones().size());
		
		usuariePremium.deshacer();
		Assert.assertEquals(CALIFICADO, atuendo.getEstado());
		Assert.assertEquals(2, usuariePremium.getDecisiones().size());
		Assert.assertEquals(Calificacion.MUCHO_FRIO, atuendo.getCalificacion());
		
		usuariePremium.deshacer();
		Assert.assertEquals(ACEPTADO, atuendo.getEstado());
		Assert.assertEquals(1, usuariePremium.getDecisiones().size());
		
		usuariePremium.deshacer();
		Assert.assertEquals(NUEVO, atuendo.getEstado());
		Assert.assertEquals(0, usuariePremium.getDecisiones().size());
	}
	
	@Test
	public void laPreferenciaSeCaldulaCorrectamente() {
		Usuarie nico = new Usuarie("Nico");
		Prenda remera = BorradorPrenda.crearRemeraRandom();
		Prenda pantalon = BorradorPrenda.crearPantalonRandom();
		Prenda zapato = BorradorPrenda.crearZapatoRandom();
		
		SensibilidadTermica sensibilidadTermica = nico.getSensibilidadTermica();
		
		Atuendo atuendoBase = new Atuendo(remera, pantalon, zapato);
		Arrays.asList(PARTE_SUPERIOR, PARTE_INFERIOR, CALZADO).forEach(categoria ->
				assertEquals(0, sensibilidadTermica.obtenerSensacionTermica(categoria, 0d).intValue()));
		nico.aceptar(atuendoBase, guardarropaLimitado, fechaTest);
		nico.calificar(atuendoBase, MUCHO_CALOR, new ImmutablePair<>(PARTE_SUPERIOR, OK),
				new ImmutablePair<>(PARTE_INFERIOR, CALOR), new ImmutablePair<>(CALZADO, MUCHO_CALOR));
		assertEquals(0D, sensibilidadTermica.obtenerDiferenciaDeTemperatura(PARTE_SUPERIOR), 0);
		assertEquals(0.5D, sensibilidadTermica.obtenerDiferenciaDeTemperatura(PARTE_INFERIOR), 0);
		assertEquals(1D, sensibilidadTermica.obtenerDiferenciaDeTemperatura(CALZADO), 0);
	}
	
	@Test
	public void luegoDeCalificarLaPreferenciaCambia() {
		Usuarie mora = new Usuarie("Mora");
		Prenda remera = BorradorPrenda.crearRemeraRandom();
		Prenda pantalon = BorradorPrenda.crearPantalonRandom();
		Prenda zapato = BorradorPrenda.crearZapatoRandom();
		
		SensibilidadTermica sensibilidadTermica = mora.getSensibilidadTermica();
		
		Atuendo atuendoBase = new Atuendo(remera, pantalon, zapato);
		assertEquals(0, sensibilidadTermica.obtenerDiferenciaDeTemperatura(PARTE_SUPERIOR), 0);
		mora.aceptar(atuendoBase, guardarropaLimitado, fechaTest);
		mora.calificar(atuendoBase, MUCHO_CALOR, new ImmutablePair<>(PARTE_SUPERIOR, MUCHO_CALOR));
		assertEquals(1D, mora.getSensibilidadTermica().obtenerDiferenciaDeTemperatura(PARTE_SUPERIOR), 0);
		mora.calificar(atuendoBase, MUCHO_CALOR, new ImmutablePair<>(PARTE_SUPERIOR, MUCHO_CALOR));
		assertEquals(2D, mora.getSensibilidadTermica().obtenerDiferenciaDeTemperatura(PARTE_SUPERIOR), 0);
	}
}
