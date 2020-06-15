package queMePongo.model.alertas;

import queMePongo.model.usuaries.Usuarie;

import java.util.List;

public enum EnumAlerta {
	TORMENTA {
		public void notificar(Usuarie usuarie) {
			Notificador.notificarLluvia(usuarie);
		}
	},
	NEVADA {
		public void notificar(Usuarie usuarie) {
			Notificador.notificarNevada(usuarie);
		}
	};
	
	public void avisar(List<Usuarie> usuarieList) {
		usuarieList.forEach(this::notificar);
	}
	
	abstract void notificar(Usuarie usuarie);
}
