package auxiliares;

public class ObtenerConfiguracion {
	
	private String rutaArchivoConfiguracion = "./files/CONFIGURACION";
	
	public String rutaContraseniasComunes() {
		LeerArchivo contraseniasComunes = new LeerArchivo(rutaArchivoConfiguracion);
        return contraseniasComunes.obtenerDato("RUTA_CONTRASENIAS_COMUNES");
	}
}
