package queMePongo.model.prendas;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrendasTest {
	
	private List<Prenda> prendas;
	private Color blanco = Color.BLANCO;
	private Color rojo = Color.ROJO;
	
	@Before
	public void generarListaDePrendas() {
		List<BorradorPrenda> borradores = Arrays.asList(
				new BorradorPrenda(TipoPrenda.sombrero),
				new BorradorPrenda(TipoPrenda.pantalon),
				new BorradorPrenda(TipoPrenda.remera),
				new BorradorPrenda(TipoPrenda.zapato));
		
		borradores.forEach(prendaBorrador -> {
			prendaBorrador.definirMaterial(Material.ALGODON);
			prendaBorrador.definirColorPrimario(blanco);
			prendaBorrador.definirTrama(Trama.RAYADA);
			prendaBorrador.definirColorSecundario(rojo);
		});

		borradores.forEach(prendaBorrador -> {prendaBorrador.definirNombre("test");});
		
		
		this.prendas = borradores.stream()
				.map(BorradorPrenda::crearPrenda).collect(Collectors.toList());
	}
	
	@Test
	public void borradorPrendasCreaPrendasOk() {
		List<Categoria> categorias = Arrays.asList(
				Categoria.ACCESORIOS,
				Categoria.PARTE_INFERIOR,
				Categoria.PARTE_SUPERIOR,
				Categoria.CALZADO);
		
		Assert.assertEquals(categorias, prendas.stream().map(Prenda::getCategoria).collect(Collectors.toList()));
		Assert.assertTrue(prendas.stream().allMatch(prenda ->
				prenda.getColorPrimario().equals(blanco) &&
						prenda.getColorSecundario().isPresent() &&
						prenda.getColorSecundario().get().equals(rojo) &&
						prenda.getMaterial().equals(Material.ALGODON) &&
						prenda.getTrama().equals(Trama.RAYADA)));
	}
	
	@Test
	public void prendaUtilParaAlgunasTemperaturas() {
		Prenda pantalon = prendas.get(1);
		Assert.assertTrue(pantalon.razonableAcordeATemperatura(15.45));
		Assert.assertTrue(pantalon.razonableAcordeATemperatura(-10.389));
		Assert.assertFalse(pantalon.razonableAcordeATemperatura(41.0));
	}
}
