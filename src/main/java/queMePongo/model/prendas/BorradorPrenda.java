package queMePongo.model.prendas;

import queMePongo.model.excepciones.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//BUILDER
public class BorradorPrenda {

	private String nombre;
	private TipoPrenda tipoPrenda;
	private Map<String, Color> colores = new HashMap<String, Color>(2) {
		{
			put("Primario", null);
			put("Secundario", null);
		}
	};
	private Trama trama = Trama.LISA;
	private Material material;
	private String foto;
	private static final String FOTOS_PATH = "src/main/resources/public/fotos/";
	
	public BorradorPrenda(TipoPrenda unTipo) {
		this.tipoPrenda = unTipo;
	}

	public void definirNombre(String nombre) {
		this.nombre = nombre.replaceAll("\\+", " ");
	}
	
	public void definirColorPrimario(Color unColor) {
		this.validarQueColorNoEsteDefinido(unColor);
		this.colores.put("Primario", unColor);
	}
	
	public void definirColorSecundario(Color unColor) {
		this.validarQueColorNoEsteDefinido(unColor);
		this.colores.put("Secundario", unColor);
	}

    /*así con todos, que no sean setters pero que tengan la idea de que configuramos el dato en nuestro
    borrador*/
	
	public void definirMaterial(Material unMaterial) {
		if (tipoPrenda == null) {
			throw new FaltaComponenteException();
		}
		if (!tipoPrenda.permiteMaterial(unMaterial)) {
			throw new MaterialNoPermitidoException();
		}
		this.material = unMaterial;
	}
	
	public void definirTrama(Trama unaTrama) {
		if (unaTrama == null) {
			throw new TramaInvalidaException();
		}
		this.trama = unaTrama;
	}
	
	public void agregarFoto(String rutaRelativa) {
		this.foto = rutaRelativa;
	}
	
	public Prenda crearPrenda() {
		this.validarQuePrendaSeaValida();
		return new Prenda(tipoPrenda, material, trama, colores.get("Primario"), colores.get("Secundario"), foto, nombre);
	}
	
	private void validarQuePrendaSeaValida() {
		if (tipoPrenda == null || colores.get("Primario") == null || material == null | nombre.isEmpty()) {
			throw new FaltaComponenteException();
		}
	}

	public TipoPrenda getTipoPrenda() {
		return this.tipoPrenda;
	}
	
	private void validarQueColorNoEsteDefinido(Color unColor) {
		if (colores.containsValue(unColor) && unColor != null) {
			throw new ColorYaDefinidoException();
		}
	}
	
	public static Prenda crearRemeraRandom() {
		return new Prenda(
				TipoPrenda.remera,
				Material.CUERO,
				Trama.RAYADA,
				Color.AZUL,
				Color.AZUL,
				"https://http2.mlstatic.com/D_NQ_NP_667800-MLA31892106525_082019-W.jpg",
				"Remera de Cuero Rayada");
	}
	
	public static Prenda crearPantalonRandom() {
		return new Prenda(
				TipoPrenda.pantalon,
				Material.CUERO,
				Trama.LISA,
				Color.NEGRO,
				null,
				"https://http2.mlstatic.com/pantalon-cuero-cuero-moto-o-calle-cuerosol-fabrica-D_NQ_NP_914424-MLA31021831099_062019-Q.jpg",
				"Pantalon de Cuero Liso");
	}
	
	public static Prenda crearZapatoRandom() {
		return new Prenda(
				TipoPrenda.zapato,
				Material.CUERO,
				Trama.LISA,
				Color.BLANCO,
				null,
				"https://us.123rf.com/450wm/andreahast/andreahast1506/andreahast150600025/40862010-zapato-blanco-de-tac%C3%B3n-alto-en-trazado-de-recorte-blanco.jpg?ver=6",
				"Zapato de Cuero Liso");
	}
	
	public static Prenda crearAccesorioRandom() {
		return new Prenda(
				TipoPrenda.sombrero,
				Material.ALGODON,
				Trama.RAYADA,
				Color.BLANCO,
				Color.NEGRO,
				"https://m.eldiario.es/tecnologia/hackers-llevan-sombrero-blanco-negro_EDIIMA20160414_0709_1.jpg",
				"Sombrero De Algodon Rayado");
	}
	
	public static Prenda crearCamperaRandom() {
		return new Prenda(
				TipoPrenda.campera,
				Material.ALGODON,
				Trama.RAYADA,
				Color.VERDE,
				Color.BLANCO,
				null,
				"Campera de Algodon Rayada");
	}
	
	public static Prenda crearBuzoRandom() {
		return new Prenda(
				TipoPrenda.buzo,
				Material.ALGODON,
				Trama.RAYADA,
				Color.BLANCO,
				Color.NEGRO,
				null,
				"Buzo de Algodon Rayado");
	}
	
	public static Prenda crearRemeraLargaRandom() {
		return new Prenda(
				TipoPrenda.remeraLarga,
				Material.ALGODON,
				Trama.RAYADA,
				Color.BLANCO,
				Color.NEGRO,
				null,
				"Remera Larga de Algodon Rayada");
	}
	
	public static Prenda crearBufandaRandom() {
		return new Prenda(
				TipoPrenda.bufanda,
				Material.ALGODON,
				Trama.LISA,
				Color.BLANCO,
				Color.NEGRO,
				null,
				"Bufanda de Algodon Lisa");
	}
}
