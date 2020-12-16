package inicioSesion;

import excepciones.ContraseniaInvalidaException;

public class ContraseniaContieneMayus implements ValidacionContrasenia {
        
    public boolean validacionContrasenia(String password) {
        if (password.chars().filter(Character::isUpperCase).count() > 0) {
        	return true;
        }else{
            throw new ContraseniaInvalidaException(this.mensajeException());
        }
    }
    
	public String mensajeException() {
		return "no contiene ninguna mayuscula";
	}

}
