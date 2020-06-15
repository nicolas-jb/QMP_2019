package queMePongo.model.prendas;

import org.junit.Test;
import queMePongo.spark.wrappers.prendas.AtributosPrendaWrapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class AtributosPrendaWrapperTest {

    private AtributosPrendaWrapper atributos = new AtributosPrendaWrapper();

    @Test
    public void estanTodosLosTipoPrenda() {
        Set<TipoPrenda> tipoPrendas = new HashSet<>(Arrays.asList(TipoPrenda.remera, TipoPrenda.bufanda,
                TipoPrenda.campera, TipoPrenda.remeraLarga, TipoPrenda.ojota, TipoPrenda.zapato,
                TipoPrenda.sombrero, TipoPrenda.pantalon, TipoPrenda.buzo, TipoPrenda.guantes, TipoPrenda.pantalonCorto));

        assertTrue(tipoPrendas.containsAll(atributos.getTipoPrendas().values()));
    }

    @Test
    public void estanTodasLasTramas() {
        Set<Trama> tramas = new HashSet<>(Arrays.asList(Trama.values()));

        assertEquals(tramas, atributos.getTramas());
    }

    @Test
    public void estanTodosLosColores() {
        Set<Color> colores = new HashSet<>(Arrays.asList(Color.values()));

        assertEquals(colores, atributos.getColores());
    }
}
