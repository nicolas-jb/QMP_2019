package queMePongo.spark.wrappers.prendas;

import lombok.Data;
import queMePongo.model.prendas.Color;
import queMePongo.model.prendas.Material;
import queMePongo.model.prendas.TipoPrenda;
import queMePongo.model.prendas.Trama;

import java.util.*;

@Data
public class AtributosPrendaWrapper {

    private HashMap<String, TipoPrenda> tipoPrendas = new HashMap<String, TipoPrenda>() {
        {
            put("Remera", TipoPrenda.remera);
            put("Bufanda", TipoPrenda.bufanda);
            put("Campera", TipoPrenda.campera);
            put("Remera Larga", TipoPrenda.remeraLarga);
            put("Ojota", TipoPrenda.ojota);
            put("Zapato", TipoPrenda.zapato);
            put("Sombrero", TipoPrenda.sombrero);
            put("Pantalon", TipoPrenda.pantalon);
            put("Pantalon Corto",TipoPrenda.pantalonCorto);
            put("Buzo", TipoPrenda.buzo);
            put("Guantes", TipoPrenda.guantes);
            put("PantalonCorto", TipoPrenda.pantalon);
        }
    };

    public HashMap<String, TipoPrenda> getTipoPrendas() {
        return tipoPrendas;
    }

    private Set<Trama> tramas = new HashSet<>(Arrays.asList(Trama.values()));

    private Set<Color> colores = new HashSet<>(Arrays.asList(Color.values()));

    private TipoPrenda tipoPrenda;

    public AtributosPrendaWrapper(){}

    public AtributosPrendaWrapper(TipoPrenda tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }

    public List<Material> getMateriales() {
        return tipoPrenda.getMaterialesQueAcepta();
    }

    public TipoPrenda getTipoPrenda(String tipoPrenda) {
        return tipoPrendas.get(tipoPrenda);
    }
}
