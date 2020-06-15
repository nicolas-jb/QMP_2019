package db;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.AtuendoUtils;
import queMePongo.model.atuendos.Calificacion;
import queMePongo.model.guardarropas.Capacidad;
import queMePongo.model.guardarropas.Guardarropa;
import queMePongo.model.guardarropas.Sugerencia;
import queMePongo.model.prendas.BorradorPrenda;
import queMePongo.model.prendas.Categoria;
import queMePongo.model.prendas.Prenda;
import queMePongo.model.usuaries.Evento;
import queMePongo.model.usuaries.SensibilidadTermica;
import queMePongo.model.usuaries.Usuarie;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PersistenciaTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
	
	@Before
	public void setup() {
		beginTransaction();
	}
	
	@Test
	public void PrueboCreacionDeQuery() {
		createQuery("from Usuarie ", Usuarie.class).
				getResultList();
	}
	
	@Test
	public void PersistoUsuarie() {
		Usuarie usuarie = new Usuarie("Nico");
		entityManager().persist(usuarie);
		assertTrue(entityManager().contains(usuarie));
	}
	
	@Test
	public void obtengoIdDeUsuariePersistido() {
		Usuarie usuarie = new Usuarie("Nico");
		entityManager().persist(usuarie);
		long expectedId = usuarie.getId();
		Usuarie usuarieObtenido = PerThreadEntityManagers.getEntityManager().find(Usuarie.class, expectedId);
		assertEquals(expectedId, usuarieObtenido.getId());
	}
	
	@Test
	public void vuelveATraerUnaPreferencia() {
		Usuarie usuarie = new Usuarie("Tom");
		Pair<Categoria, Calificacion> calificacion = new ImmutablePair<>(Categoria.PARTE_SUPERIOR, Calificacion.MUCHO_FRIO);
		usuarie.getSensibilidadTermica()
				.agregarCalificacion(calificacion);
		entityManager().persist(usuarie);
		List<SensibilidadTermica> sensibilidadTermicas = entityManager().createQuery("select p from SensibilidadTermica as p", SensibilidadTermica.class).getResultList();
		assertTrue(sensibilidadTermicas.stream().anyMatch(sensibilidadTermica -> sensibilidadTermica.getPreferencias().containsKey(Categoria.PARTE_SUPERIOR)));
		
	}
	
	@Test
	public void sePersisteUnaPrenda() {
		Prenda remera = BorradorPrenda.crearBufandaRandom();
		entityManager().persist(remera);
		List<Prenda> remerasPersistidas = entityManager().createQuery("select p from Prenda as p", Prenda.class).getResultList();
		assertTrue(remerasPersistidas.contains(remera));
	}
	
	@Test
	public void seGeneraIdCorrelativo() throws Exception {
		Usuarie usuarie = new Usuarie("Nico");
		Usuarie usuarie1 = new Usuarie("Guido");
		Usuarie usuarie2 = new Usuarie("Fran");
		Usuarie usuarie3 = new Usuarie("Tom");
		Usuarie usuarie4 = new Usuarie("Mora");
		
		entityManager().persist(usuarie);
		long primerId = usuarie.getId();
		entityManager().persist(usuarie1);
		entityManager().persist(usuarie2);
		entityManager().persist(usuarie3);
		entityManager().persist(usuarie4);
		
		assertEquals(primerId, usuarie.getId());
		assertEquals(primerId + 1, usuarie1.getId());
		assertEquals(primerId + 2, usuarie2.getId());
		assertEquals(primerId + 3, usuarie3.getId());
		assertEquals(primerId + 4, usuarie4.getId());
	}
	
	@Test
	public void sePersistenLosAtuendos() {
		Atuendo atuendo = AtuendoUtils.getAtuendoCompleto();
		entityManager().persist(atuendo);
		List<Atuendo> atuendosPersistidos = entityManager().createQuery("select a from Atuendo as a", Atuendo.class).getResultList();
		assertTrue(atuendosPersistidos.contains(atuendo));
	}
	
	@Test
	public void sePersistenLosEventos() {
		Evento evento = new Evento(LocalDate.now(), "EventoDePrueba");
		entityManager().persist(evento);
		Evento eventoPersistido = entityManager().createQuery("select e from Evento as e", Evento.class).getSingleResult();
		assertEquals(evento, eventoPersistido);
	}
	
	@Test
	public void sePersisteUnGuardarropa() {
		Guardarropa guardarropa = new Guardarropa();
		guardarropa.setCapacidad(Capacidad.LIMITADA);
		guardarropa.setPrendas(Arrays.asList(
				BorradorPrenda.crearRemeraRandom(),
				BorradorPrenda.crearPantalonRandom(),
				BorradorPrenda.crearAccesorioRandom(),
				BorradorPrenda.crearBufandaRandom(),
				BorradorPrenda.crearZapatoRandom()));
		guardarropa.setNombre("Armario");
		entityManager().persist(guardarropa);
		Guardarropa guardarropaPersistido = entityManager().createQuery("select g from Guardarropa as g where g.id="+guardarropa.getId(), Guardarropa.class).getSingleResult();
		assertEquals(guardarropaPersistido.getId(),guardarropa.getId());
	}
	
	@Test
	public void sePersistenUsuariosConContraseña() {
		Arrays.asList(
				new Usuarie("user01", "pass"), new Usuarie("user02", "pass"),
				new Usuarie("user01", "unaPassReLoca"), new Usuarie("user03", "pass"))
				.forEach(usuarie -> entityManager().persist(usuarie));
	}
	
	@Test
	public void sePersisteUnaSugerencia() {
		entityManager().persist(new Sugerencia());
	}
	
	@After
	public void tearDown() {
		commitTransaction();
	}
	
}
