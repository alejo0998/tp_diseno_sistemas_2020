package excepciones;
public class RecursosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecursosException(String causa){
        super("Ocurri� el siguiente error con un recurso: " + causa);
    }
}
