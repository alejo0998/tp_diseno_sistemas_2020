package inicioSesion;

import excepciones.ContraseniaInvalidaException;

public class ContraseniaLongitudApropiada implements ValidacionContrasenia {
        
    public boolean validacionContrasenia(String password) {
        if (password.length() >= 8) {
        	return true;
        }else{
            throw new ContraseniaInvalidaException(this.mensajeException());
        }
    }
    
	public String mensajeException() {
		return "es muy corta";
	}

}
