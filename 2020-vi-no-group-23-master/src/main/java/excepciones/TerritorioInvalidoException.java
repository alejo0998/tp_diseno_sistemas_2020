package excepciones;
public class TerritorioInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TerritorioInvalidoException(){
        super("El codigo postal proporcionado es inválido");
    }

}
