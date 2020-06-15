package queMePongo.model.usuaries;

import org.junit.Before;
import org.junit.Test;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.atuendos.AtuendoUtils;
import queMePongo.model.guardarropas.Sugerencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class EventoTest {
	
	private Evento evento;
	
	@Before
	public void crearEvento() {
		evento = new Evento(LocalDate.now(), "Test");
	}
	
	@Test
	public void unEventoAgregaSugerenciasAUnUsuarioMockeado() {
		Usuarie usuarie = mock(Usuarie.class);
		List<Atuendo> atuendoList = Arrays.asList(mock(Atuendo.class), mock(Atuendo.class));
		List<Sugerencia> sugerencias = atuendoList.stream().map(atuendo -> new Sugerencia(usuarie, atuendo)).collect(Collectors.toList());
		
		evento.agregarSugerencias(sugerencias);
		assertEquals(sugerencias, evento.getSugerenciasDeUnUsuario(usuarie));
	}
	
	@Test
	public void unEventoAgregaSugerenciasAUnUsuarioSinMocks() {
		Usuarie usuarie = new Usuarie("Franquito");
		List<Atuendo> atuendoList = Arrays.asList(AtuendoUtils.getAtuendoBase(), AtuendoUtils.getAtuendoCompleto());
		List<Sugerencia> sugerencias = atuendoList.stream().map(atuendo -> new Sugerencia(usuarie, atuendo)).collect(Collectors.toList());
		
		evento.agregarSugerencias(sugerencias);
		assertEquals(sugerencias, evento.getSugerenciasDeUnUsuario(usuarie));
	}
	
	@Test
	public void unEventoAgregaSugerenciasAUnUsuarioConSugerenciasAnteriores() {
		Usuarie usuarie = new Usuarie("Franquito");
		List<Atuendo> atuendoList = new ArrayList<>(Arrays.asList(AtuendoUtils.getAtuendoBase(), AtuendoUtils.getAtuendoCompleto()));
		List<Sugerencia> sugerencias = atuendoList.stream().map(atuendo -> new Sugerencia(usuarie, atuendo)).collect(Collectors.toList());
		
		evento.agregarSugerencias(sugerencias);
		assertEquals(sugerencias, evento.getSugerenciasDeUnUsuario(usuarie));
		
		Atuendo nuevoAtuendo = AtuendoUtils.getAtuendoIntermedio();
		evento.agregarSugerencia(new Sugerencia(usuarie, nuevoAtuendo));
		
		sugerencias.add(new Sugerencia(usuarie, nuevoAtuendo));
		assertEquals(sugerencias, evento.getSugerenciasDeUnUsuario(usuarie));
	}
	
	@Test
	public void sucedeEnMenosDe7Dias() {
		evento = new Evento(LocalDate.now().plusDays(6), "Testcito");
		assertTrue(evento.sucedeEnMenosDeTantosDias(7));
	}
	
	@Test
	public void sucedeEnMenosDe10DiasEsFalso() {
		evento = new Evento(LocalDate.now().plusDays(10), "Testcito");
		assertFalse(evento.sucedeEnMenosDeTantosDias(7));
	}
}
