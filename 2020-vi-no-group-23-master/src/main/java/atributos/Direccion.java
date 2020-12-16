package atributos;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class Direccion {
	
	@SuppressWarnings("unused")
	private String calle;
	@SuppressWarnings("unused")
	private int altura;
	@Column(nullable=true)
	private int piso;
	@Column(nullable=true)
	private int departamento;

	public Direccion(String calleD, int alturaD, int pisoD, int departamentoD) {
		calle = calleD;
		altura = alturaD;
		piso = pisoD;
		departamento = departamentoD;
	}
}
