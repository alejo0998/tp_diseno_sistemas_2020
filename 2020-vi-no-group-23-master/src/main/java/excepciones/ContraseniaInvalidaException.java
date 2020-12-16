package excepciones;

public class ContraseniaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ContraseniaInvalidaException(String causa){
        super("La contrasenia es invalida porque: " + causa);
    }

}
