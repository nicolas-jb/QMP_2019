package queMePongo.model.prendas;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import queMePongo.model.excepciones.*;

import java.util.Arrays;

public class BorradorPrendaTest {

    private BorradorPrenda borrador;

    @Before
    public void inicializacionPrenda() {
        borrador = new BorradorPrenda(TipoPrenda.zapato);
        borrador.definirNombre("Zapatito");
        borrador.definirMaterial(Material.ALGODON);
        borrador.definirColorPrimario(Color.BLANCO);
        borrador.definirColorSecundario(Color.VERDE);
    }

    @Test(expected = MaterialNoPermitidoException.class)
    public void lanzaExcepcionMaterialNoPermitido() {
        borrador.definirMaterial(Material.NYLON);
    }

    @Test(expected = FaltaComponenteException.class)
    public void lanzaExcepcionAlFaltarColorDelBorrador() {
        borrador.definirColorPrimario(null);
        borrador.crearPrenda();
    }

    @Test(expected = TramaInvalidaException.class)
    public void lanzaExcepcionTramaInvalida() {
        borrador.definirTrama(null);
    }

    @Test(expected = ColorYaDefinidoException.class)
    public void lanzaExcepcionAlRepetirColor() {
        borrador.definirColorSecundario(Color.BLANCO);
        Prenda prenda = borrador.crearPrenda();

        Assert.assertNotEquals(prenda.getColorPrimario(), prenda.getColorSecundario());
    }

    @Test
    public void nivelDeAbrigoAbarcaUnIntervalo() {
        TipoPrenda buffanda = new TipoPrenda("Bufanda", Categoria.ACCESORIOS, Arrays.asList(Material.ALGODON), Capa.BASE, new NivelDeAbrigo(-20, 15));
        Assert.assertTrue(buffanda.razonableAcordeATemperatura(0.0));
        Assert.assertTrue(buffanda.razonableAcordeATemperatura(-20.0));
        Assert.assertFalse(buffanda.razonableAcordeATemperatura(50.0));
    }

    @Test
    public void crearUnaPrenda() {
        Prenda prenda = borrador.crearPrenda();
        Assert.assertEquals(prenda.getTipoPrenda(), TipoPrenda.zapato);
        Assert.assertEquals(prenda.getMaterial(), Material.ALGODON);
        Assert.assertEquals(prenda.getTrama(), Trama.LISA); // Al mismo tiempo se chequea que se haya creado con su trama default
        Assert.assertEquals(prenda.getColorPrimario(), (Color.BLANCO));
        Assert.assertEquals(prenda.getColorSecundario(), java.util.Optional.of((Color.VERDE)));
    }
}
