package queMePongo.model.atuendos;

public enum Estado {
	ACEPTADO(true),
	CALIFICADO(true),
	NUEVO(false),
	RECHAZADO(false);
	
	private Boolean permiteCalificar;
	
	Estado(Boolean permiteCalificar) {
		this.permiteCalificar = permiteCalificar;
	}
	
	public Boolean permiteCalificar() {
		return this.permiteCalificar;
	}
}
