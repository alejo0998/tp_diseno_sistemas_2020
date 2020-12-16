package excepciones;
public class CodigoPostalInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CodigoPostalInvalidoException(String causa){
        super("El codigo postal es invalido porque: " + causa);
    }

}
