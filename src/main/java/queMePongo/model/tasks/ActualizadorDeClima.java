package queMePongo.model.tasks;

import queMePongo.model.alertas.EnumAlerta;
import queMePongo.model.alertas.Notificador;
import queMePongo.model.clima.ProveedorDeClima;
import queMePongo.model.repositorios.RepoDeUsuarios;
import queMePongo.model.usuaries.Usuarie;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ActualizadorDeClima {
	
	public static void main(String[] args) {
		
		ProveedorDeClima proveedorDeClima = ProveedorDeClima.getInstance();
		
		RepoDeUsuarios repoDeUsuarios = RepoDeUsuarios.getInstance();
		
		Usuarie mora = new Usuarie("Tom");
		Usuarie tom = new Usuarie("Mora");
		
		repoDeUsuarios.agregarUsuarie(mora);
		repoDeUsuarios.agregarUsuarie(tom);
		
		Notificador.addInteresadoEnMail(tom);
		Notificador.addInteresadoEnText(tom);
		Notificador.addInteresadoEnMail(mora);
		
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				List<EnumAlerta> enumAlertas = Arrays.asList(EnumAlerta.TORMENTA, EnumAlerta.NEVADA);
				enumAlertas.forEach(enumAlerta -> enumAlerta.avisar(repoDeUsuarios.getUsuaries()));
				
			}
		}, 0L, 1000L * 2L);
		
	}
}
