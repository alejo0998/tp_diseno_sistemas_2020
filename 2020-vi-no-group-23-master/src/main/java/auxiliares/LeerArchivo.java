package auxiliares;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import excepciones.RecursosException;

public class LeerArchivo {
	private String ruta;
	
	public LeerArchivo(String ruta) {
		this.ruta = ruta;
	}

	public String obtenerContenido() {
        String contenido = "";
    	try
    	{
    		BufferedReader br = new BufferedReader(new FileReader(ruta));
    		String linea = br.readLine();
    		while (linea != null)
    		{
    			contenido += linea;
    			linea = br.readLine();
    		}
    		br.close();
    	} 
    	catch (IOException e) 
    	{
    		throw new RecursosException(e.toString());
    	}
    	return contenido;
	}
	
	public String obtenerDato(String dato) {
        String valor = "";
    	try
    	{
    		BufferedReader br = new BufferedReader(new FileReader(ruta));
    		String linea = br.readLine();
    		while (linea != null)
    		{
    			if (linea.startsWith(dato)) {
    				valor = linea.split(" = ")[1];
    				break;
    			}
    			linea = br.readLine();
    		}
    		br.close();
    	} 
    	catch (IOException e) 
    	{
    		throw new RecursosException(e.toString());
    	}
    	return valor;
	}
}
