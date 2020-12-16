package excepciones;

public class EntidadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadException(String causa){
        super("La operación sobre la entidad es inválida porque: " + causa);
    }

}
