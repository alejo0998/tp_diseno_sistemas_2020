package pagos;

public enum MetodoPago {
	TARJETA_DE_CREDITO("Tarjeta de credito"),
	TARJETA_DE_DEBITO("Tarjeta de debido"),
	EFECTIVO("Efectivo"),
	CAJERO_AUTOMATICO("Cajero automatico"),
	DINERO_EN_CUENTA("Dinero en cuenta");
	
	private String valor;
	
	MetodoPago(String valor) {
	      this.valor = valor;
	}
	
	public String getValor() {
	      return valor;
	}
}
