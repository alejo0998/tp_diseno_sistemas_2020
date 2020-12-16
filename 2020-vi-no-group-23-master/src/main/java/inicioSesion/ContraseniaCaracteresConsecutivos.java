package inicioSesion;

import excepciones.ContraseniaInvalidaException;

import java.util.stream.IntStream;

public class ContraseniaCaracteresConsecutivos implements ValidacionContrasenia {
        
	public boolean validacionContrasenia(String password) throws ContraseniaInvalidaException {

        int[] arrayDeIndices = IntStream.range(0, password.length()-1).toArray();

        for(int i : arrayDeIndices) {
            if (password.charAt(i) + 1 == password.charAt(i+1)) {
                throw new ContraseniaInvalidaException(this.mensajeException());
            }
        }
        return true;
    }
	
	public String mensajeException() {
	    return "tiene caracteres consecutivos";
	}

}
