package excepciones;
public class MonedaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MonedaInvalidaException(){
        super("El ID proporcionado es inválido");
    }

}
