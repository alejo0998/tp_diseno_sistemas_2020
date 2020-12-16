package item;

public enum TipoItem {
	
	PRODUCTO("Producto"),
	SERVICIO("Servicio");
	
	private String valor;
	
	TipoItem(String valor) {
	      this.valor = valor;
	}
	
	public String getValor() {
	      return valor;
	}

}
