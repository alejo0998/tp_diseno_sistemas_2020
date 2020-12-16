package inicioSesion;

import org.mindrot.jbcrypt.BCrypt;

import usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CreadorDeUsuarios {

    private List<ValidacionContrasenia> listaValidaciones = new ArrayList<>();

    //constructor
    public Usuario crearUsuario (String nombre, String contrasenia, TipoUsuario tipo_usuario) {
    	this.validarContrasenia(contrasenia);
    	return new Usuario(nombre, this.hashearContrasenia(contrasenia), tipo_usuario);
    }
    
    private void validarContrasenia(String contrasenia) {
    	for (ValidacionContrasenia validacion : listaValidaciones) {
            validacion.validacionContrasenia(contrasenia);
        }
    }
    
    public String hashearContrasenia(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean chequearContraseniaHasheada(String candidate, String hashed){
        return BCrypt.checkpw(candidate, hashed);
    }

    public void setListaValidaciones(List<ValidacionContrasenia> listaValidaciones) {
        this.listaValidaciones = listaValidaciones;
    }

}
