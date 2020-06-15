package queMePongo.model.guardarropas;


public enum Capacidad {
	LIMITADA(10){
		public Boolean puedeAgregarPrenda(Integer cantidadDePrendas) {
			return cantidadDePrendas < 20;
		}
	},
	ILIMITADA(20){
		public Boolean puedeAgregarPrenda(Integer cantidadDePrendas) {
			return true;
		}
	};
	
	int cantidadDeSugerencias;
	
	Capacidad(int cantidadDeSugerencias){
		this.cantidadDeSugerencias = cantidadDeSugerencias;
	}
	
	public int getCantidadDeSugerencias(){
		return cantidadDeSugerencias;
	}
	
	public abstract Boolean puedeAgregarPrenda(Integer cantidadActualDePrendas);
	
}

