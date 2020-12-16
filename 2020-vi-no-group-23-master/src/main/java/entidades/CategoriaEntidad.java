package entidades;


public enum CategoriaEntidad {

	MICRO("Micro"),
	PEQUENIA("Pequenia"),
	MEDIANA_TRAMO_1("Mediana (Tramo 1)"),
	MEDIANA_TRAMO_2("Mediana (Tramo 2)"),
	OSC("Osc");
	
	private String valor;
	
	CategoriaEntidad(String valor) {
	      this.valor = valor;
	}
	
	public String getValor() {
	      return valor;
	}
}
