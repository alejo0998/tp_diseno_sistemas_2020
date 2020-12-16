package excepciones;

public class EntidadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadException(String causa){
        super("La operaci�n sobre la entidad es inv�lida porque: " + causa);
    }

}
