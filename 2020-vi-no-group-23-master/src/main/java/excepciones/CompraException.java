package excepciones;

public class CompraException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CompraException(String causa){
		 super("La compra ya existe en las compras " + causa);

    }

}
