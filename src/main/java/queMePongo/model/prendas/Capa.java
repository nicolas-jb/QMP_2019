package queMePongo.model.prendas;

public enum Capa {
	BASE(0),
	INTERMEDIA(1),
	FINAL(2);
	
	private Integer valor;
	
	Capa(Integer valor) {
		this.valor = valor;
	}
	
	public Integer getValor() {
		return valor;
	}
}
