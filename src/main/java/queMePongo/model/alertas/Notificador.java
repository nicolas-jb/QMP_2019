package queMePongo.model.alertas;

import queMePongo.model.guardarropas.Sugerencia;
import queMePongo.model.usuaries.Usuarie;

import java.util.ArrayList;
import java.util.List;

public class Notificador {
	
	static List<Usuarie> interesadosEnMail = new ArrayList<>();
	static List<Usuarie> interesadosEnTexto = new ArrayList<>();
	
	static public void addInteresadoEnMail(Usuarie usuarie) {
		interesadosEnMail.add(usuarie);
	}
	
	static public void addInteresadoEnText(Usuarie usuarie) {
		interesadosEnTexto.add(usuarie);
	}
	
	static public void notificarLluvia(Usuarie usuarie) {
		if (interesadosEnMail.contains(usuarie))
			System.out.println("Mail de que llueve");
		if (interesadosEnTexto.contains(usuarie))
			System.out.println("Texto que llueve");
	}
	
	static public void notificarNevada(Usuarie usuarie) {
		if (interesadosEnMail.contains(usuarie))
			System.out.println("Mail de que graniza");
		if (interesadosEnTexto.contains(usuarie))
			System.out.println("Texto que graniza");
	}
	
	static public void notificarNuevaSugerencia(Usuarie usuarie, List<Sugerencia> sugerencias) {
		System.out.println(String.format("El usuario: %s tiene %d nuevas sugerencias", usuarie, sugerencias.size()));
	}
	
}
