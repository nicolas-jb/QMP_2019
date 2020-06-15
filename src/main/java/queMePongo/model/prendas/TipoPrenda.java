package queMePongo.model.prendas;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor
public class TipoPrenda {
	@Id
	@GeneratedValue
	private long id;
	private String nombre;
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Material> materialesQueAcepta;
	@Enumerated(EnumType.STRING)
	private Capa capa;
	@Embedded
	private NivelDeAbrigo nivelDeAbrigo;
	
	public TipoPrenda(String nombre, Categoria categoria, List<Material> materialesQueAcepta, Capa capa, NivelDeAbrigo nivelDeAbrigo) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.materialesQueAcepta = materialesQueAcepta;
		this.capa = capa;
		this.nivelDeAbrigo = nivelDeAbrigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public List<Material> getMaterialesQueAcepta() {
		return this.materialesQueAcepta;
	}

	public Capa getCapa() {
		return capa;
	}
	
	public boolean razonableAcordeATemperatura(Double temperatura) {
		return nivelDeAbrigo.cubre(temperatura);
	}
	
	boolean permiteMaterial(Material unMaterial) {
		return materialesQueAcepta.contains(unMaterial);
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	
	public static final TipoPrenda zapato = new TipoPrenda("Zapato", Categoria.CALZADO, Arrays.asList(
			Material.ALGODON, Material.CUERO, Material.JEAN), Capa.BASE, new NivelDeAbrigo(-20, 50));
	public static final TipoPrenda remera = new TipoPrenda("Remera", Categoria.PARTE_SUPERIOR, Arrays.asList(
			Material.ALGODON, Material.CUERO, Material.JEAN, Material.NYLON, Material.LYCRA), Capa.BASE, new NivelDeAbrigo(-20, 60));
	public static final TipoPrenda pantalon = new TipoPrenda("Pantalon", Categoria.PARTE_INFERIOR, Arrays.asList(
			Material.ALGODON, Material.CUERO, Material.JEAN, Material.NYLON, Material.LYCRA), Capa.BASE, new NivelDeAbrigo(-20, 40));
	public static final TipoPrenda sombrero = new TipoPrenda("Sombrero", Categoria.ACCESORIOS, Arrays.asList(
			Material.ALGODON, Material.CUERO, Material.JEAN, Material.NYLON, Material.LYCRA), Capa.BASE, new NivelDeAbrigo(0, 25));
	public static final TipoPrenda buzo = new TipoPrenda("Buzo", Categoria.PARTE_SUPERIOR, Arrays.asList(
			Material.ALGODON, Material.CUERO), Capa.INTERMEDIA, new NivelDeAbrigo(-20, 20));
	public static final TipoPrenda campera = new TipoPrenda("Campera", Categoria.PARTE_SUPERIOR, Arrays.asList(
			Material.ALGODON, Material.CUERO), Capa.FINAL, new NivelDeAbrigo(-20, 10));
	public static final TipoPrenda ojota = new TipoPrenda( "Ojotas", Categoria.PARTE_INFERIOR, Arrays.asList(
			Material.CUERO,Material.LYCRA), Capa.BASE, new NivelDeAbrigo(20, 60));
	public static final TipoPrenda pantalonCorto = new TipoPrenda("Pantalon Corto", Categoria.PARTE_INFERIOR, Arrays.asList(
			Material.ALGODON, Material.JEAN), Capa.BASE, new NivelDeAbrigo(15, 60));
	public static final TipoPrenda remeraLarga = new TipoPrenda("Remera larga", Categoria.PARTE_SUPERIOR, Arrays.asList(
			Material.ALGODON,Material.NYLON, Material.LYCRA), Capa.BASE, new NivelDeAbrigo(-20, 60));
	public static final TipoPrenda bufanda = new TipoPrenda("Bufanda", Categoria.ACCESORIOS, Arrays.asList(
			Material.ALGODON, Material.LYCRA), Capa.BASE, new NivelDeAbrigo(-30, 20));
	public static final TipoPrenda guantes = new TipoPrenda("Guantes", Categoria.ACCESORIOS, Arrays.asList(
			Material.CUERO, Material.NYLON), Capa.BASE, new NivelDeAbrigo(-30, 15));
}
