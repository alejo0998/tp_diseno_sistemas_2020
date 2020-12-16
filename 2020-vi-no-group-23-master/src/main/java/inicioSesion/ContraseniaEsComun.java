package inicioSesion;

import auxiliares.LeerArchivo;
import auxiliares.ObtenerConfiguracion;
import excepciones.ContraseniaInvalidaException;

public class ContraseniaEsComun implements ValidacionContrasenia {
    
    public boolean validacionContrasenia(String contrasenia){
    	ObtenerConfiguracion configuracion = new ObtenerConfiguracion();
    	String rutaContraseniasComunes = configuracion.rutaContraseniasComunes();
		LeerArchivo contraseniasComunes = new LeerArchivo(rutaContraseniasComunes);
        String contenido = contraseniasComunes.obtenerContenido();
		if (!contenido.contains(contrasenia)) {
			return true;
		} else {
			throw new ContraseniaInvalidaException(this.mensajeException());
		}
    }
	public String mensajeException() {
		return "es muy debil";
	}

}
