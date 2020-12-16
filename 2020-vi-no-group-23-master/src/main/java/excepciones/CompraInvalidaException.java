package excepciones;

public class CompraInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CompraInvalidaException(String causa){
        super("La compra es invalida porque: " + causa);
    }

}
