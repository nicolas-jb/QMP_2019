package queMePongo.model.usuaries;

import queMePongo.model.guardarropas.Guardarropa;

public enum  Suscripcion {
	GRATUITA{
		public boolean puedeAgregarGuardarropa(Guardarropa guardarropa){
			return false;
		}
	},
	PREMIUM{
		public boolean puedeAgregarGuardarropa(Guardarropa guardarropa){
			return true;
		}
	};
	
	public abstract boolean puedeAgregarGuardarropa(Guardarropa guardarropa);
}
