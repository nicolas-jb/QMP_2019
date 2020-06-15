package queMePongo.model.excepciones;

public class NoSePuedeParsearLaFecha extends RuntimeException {
	
	public NoSePuedeParsearLaFecha(String unParsedDate) {
		super("No se pudo parsear la fecha: " + unParsedDate);
	}
}
