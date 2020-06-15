package queMePongo.model.guardarropas;

import com.google.common.collect.Sets;
import queMePongo.model.atuendos.Atuendo;
import queMePongo.model.clima.ProveedorDeClima;
import queMePongo.model.excepciones.FaltanPrendasException;
import queMePongo.model.prendas.Capa;
import queMePongo.model.prendas.Categoria;
import queMePongo.model.prendas.Prenda;
import queMePongo.model.usuaries.SensibilidadTermica;
import queMePongo.model.usuaries.Usuarie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static queMePongo.model.prendas.Categoria.*;

public class GeneradorDeSugerencias {
	
	private final static double MINIMO2CAPAS = 16;
	private final static double MINIMO3CAPAS = 10;
	
	private static GeneradorDeSugerencias ourInstance = new GeneradorDeSugerencias();
	private ProveedorDeClima proveedorDeClima = ProveedorDeClima.getInstance();
	
	static GeneradorDeSugerencias getInstance() {
		return ourInstance;
	}
	
	private GeneradorDeSugerencias() {
	}
	
	public List<Sugerencia> generarSugerencias(List<Prenda> prendas, Usuarie usuarie) {
		Double temperatura = proveedorDeClima.obtenerTemperatura();
		return generarSugerenciaSegunLaTemperatura(temperatura, prendas, usuarie);
	}
	
	
	public List<Sugerencia> generarSugerenciaSegunLaTemperatura(Double temperatura, List<Prenda> prendas, Usuarie usuarie) {
		SensibilidadTermica sensibilidadTermica = usuarie.getSensibilidadTermica(); //Usa la sensibilidad para modificar los valores por default
		Double sensacionTermicaParteSuperior = sensibilidadTermica.obtenerSensacionTermica(PARTE_SUPERIOR, temperatura);
		
		List<List<Prenda>> prendasFiltradas = Stream.of(PARTE_SUPERIOR, PARTE_INFERIOR, CALZADO, ACCESORIOS)
				.map(categoria -> filtrarPrendasPorCategoriaYClima(prendas, categoria, sensibilidadTermica, temperatura))
				.collect(Collectors.toList());
		
		List<Prenda> partesSuperiores = prendasFiltradas.get(0);
		List<Prenda> partesInferiores = prendasFiltradas.get(1);
		List<Prenda> calzado = prendasFiltradas.get(2);
		List<Prenda> accesorios = prendasFiltradas.get(3);
		
		List<Prenda> partesSuperioresBase = filtrarPrendaPorCapa(partesSuperiores, Capa.BASE);
		List<Prenda> partesSuperioresIntermedias = filtrarPrendaPorCapa(partesSuperiores, Capa.INTERMEDIA);
		List<Prenda> partesSuperioresFinales = filtrarPrendaPorCapa(partesSuperiores, Capa.FINAL);
		
		//FALTA que tenga en cuenta la sensibilidad de las manos, cabeza y cuello //
		
		if (partesSuperioresBase.isEmpty() || partesInferiores.isEmpty() || calzado.isEmpty())
			throw new FaltanPrendasException();
		
		List<Atuendo> atuendos = new ArrayList<>();
		
		if (sensacionTermicaParteSuperior > MINIMO2CAPAS) {
			Set<List<Prenda>> sugerenciasSinAccesorios = Sets.cartesianProduct(Sets.newHashSet(partesSuperioresBase),
					Sets.newHashSet(partesInferiores), Sets.newHashSet(calzado));
			Set<List<Prenda>> sugerenciasConAccesorios = Sets.cartesianProduct(Sets.newHashSet(partesSuperioresBase),
					Sets.newHashSet(partesInferiores), Sets.newHashSet(calzado), Sets.newHashSet(accesorios));
			sugerenciasConAccesorios.forEach(lista -> {
				Atuendo atuendo = new Atuendo(lista.get(0), lista.get(1), lista.get(2));
				atuendo.agregarAccesorio(lista.get(3));
				atuendos.add(atuendo);
			});
			sugerenciasSinAccesorios.forEach(lista -> atuendos.add(new Atuendo(lista.get(0), lista.get(1), lista.get(2))));
			
		}
		if ((sensacionTermicaParteSuperior <= MINIMO2CAPAS) && (sensacionTermicaParteSuperior > MINIMO3CAPAS)) {
			Set<List<Prenda>> sugerenciasSinAccesorios = Sets.cartesianProduct(Sets.newHashSet(partesSuperioresBase),
					Sets.newHashSet(partesInferiores), Sets.newHashSet(calzado), Sets.newHashSet(partesSuperioresIntermedias));
			Set<List<Prenda>> sugerenciasConAccesorios = Sets.cartesianProduct(Sets.newHashSet(partesSuperioresBase),
					Sets.newHashSet(partesInferiores), Sets.newHashSet(calzado), Sets.newHashSet(accesorios),
					Sets.newHashSet(partesSuperioresIntermedias));
			sugerenciasConAccesorios.forEach(lista -> {
				Atuendo atuendo = new Atuendo(lista.get(0), lista.get(1), lista.get(2), lista.get(4));
				atuendo.agregarAccesorio(lista.get(3));
				atuendos.add(atuendo);
			});
			sugerenciasSinAccesorios.forEach(lista ->
					atuendos.add(new Atuendo(lista.get(0), lista.get(1), lista.get(2), lista.get(3))));
			
		} else {
			Set<List<Prenda>> sugerenciasSinAccesorios = Sets.cartesianProduct(Sets.newHashSet(partesSuperioresBase),
					Sets.newHashSet(partesInferiores), Sets.newHashSet(calzado), Sets.newHashSet(partesSuperioresIntermedias),
					Sets.newHashSet(partesSuperioresFinales));
			Set<List<Prenda>> sugerenciasConAccesorios = Sets.cartesianProduct(Sets.newHashSet(partesSuperioresBase),
					Sets.newHashSet(partesInferiores), Sets.newHashSet(calzado), Sets.newHashSet(accesorios),
					Sets.newHashSet(partesSuperioresIntermedias), Sets.newHashSet(partesSuperioresFinales));
			sugerenciasConAccesorios.forEach(lista -> {
				Atuendo atuendo = new Atuendo(lista.get(0), lista.get(1), lista.get(2), lista.get(4), lista.get(5));
				atuendo.agregarAccesorio(lista.get(3));
				atuendos.add(atuendo);
			});
			sugerenciasSinAccesorios.forEach(lista ->
					atuendos.add(new Atuendo(lista.get(0), lista.get(1), lista.get(2), lista.get(3), lista.get(4))));
			
		}
		return atuendos.stream().map(atuendo -> new Sugerencia(usuarie, atuendo)).collect(Collectors.toList());
	}
	
	public List<Prenda> filtrarPrendasPorCategoriaYClima(List<Prenda> prendas, Categoria categoria, SensibilidadTermica sensibilidadTermica, Double temperatura) {
		Double sensacionTermica = sensibilidadTermica.obtenerSensacionTermica(categoria, temperatura);
		List<Prenda> prendasPorCategoria = filtrarPrendaPorCategoria(prendas, categoria);
		return filtrarPrendasAcordeAlClima(sensacionTermica, prendasPorCategoria);
	}
	
	public List<Prenda> filtrarPrendasAcordeAlClima(Double temperatura, List<Prenda> prendas) {
		return prendas.stream().filter(prenda -> prenda.razonableAcordeATemperatura(temperatura)).collect(Collectors.toList());
	}
	
	private List<Prenda> filtrarPrendaPorCategoria(List<Prenda> prendas, Categoria unaCategoria) {
		return prendas
				.stream()
				.filter(prenda -> prenda.getTipoPrenda().getCategoria().equals(unaCategoria))
				.collect(Collectors.toList());
	}
	
	private List<Prenda> filtrarPrendaPorCapa(List<Prenda> prendas, Capa unaCapa) {
		return prendas
				.stream()
				.filter(prenda -> prenda.getTipoPrenda().getCapa().equals(unaCapa))
				.collect(Collectors.toList());
	}
}
