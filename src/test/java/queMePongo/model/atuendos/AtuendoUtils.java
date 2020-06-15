package queMePongo.model.atuendos;

import queMePongo.model.prendas.BorradorPrenda;
import queMePongo.model.prendas.Prenda;

public class AtuendoUtils {
	
	private static Prenda remera = BorradorPrenda.crearRemeraRandom();
	private static Prenda pantalon = BorradorPrenda.crearPantalonRandom();
	private static Prenda zapato = BorradorPrenda.crearZapatoRandom();
	private static Prenda sombrero = BorradorPrenda.crearAccesorioRandom();
	private static Prenda buzo = BorradorPrenda.crearBuzoRandom();
	private static Prenda campera = BorradorPrenda.crearCamperaRandom();
	
	public static Atuendo getAtuendoBase() {
		return new Atuendo(remera, pantalon, zapato);
	}
	
	public static Atuendo getAtuendoIntermedio() {
		return new Atuendo(remera, pantalon, zapato, buzo);
	}
	
	public static Atuendo getAtuendoCompleto() {
		return new Atuendo(remera, pantalon, zapato, buzo, campera);
	}
	
	public static Atuendo agregarAccesorio(Atuendo atuendo) {
		atuendo.agregarAccesorio(sombrero);
		return atuendo;
	}
}
