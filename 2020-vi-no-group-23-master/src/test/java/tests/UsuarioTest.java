package tests;

import org.junit.Assert;
import org.junit.Test;

import inicioSesion.ContraseniaCaracteresConsecutivos;
import inicioSesion.ContraseniaCaracteresConsecutivosIdenticos;
import inicioSesion.ContraseniaContieneMayus;
import inicioSesion.ContraseniaEsComun;
import excepciones.ContraseniaInvalidaException;
import inicioSesion.ContraseniaLongitudApropiada;
import inicioSesion.CreadorDeUsuarios;
import inicioSesion.TipoUsuario;
import inicioSesion.ValidacionContrasenia;

import java.util.ArrayList;
import java.util.List;

public class UsuarioTest {

    private CreadorDeUsuarios creadorDeUsuarios;
    private List<ValidacionContrasenia> listaValidaciones = new ArrayList<>();
    
    @Test
    public void passwordInvalidoPorSerComun() {
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaEsComun());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
    	try {
            creadorDeUsuarios.crearUsuario("juan", "123456", TipoUsuario.ADMINISTRADOR);
    	} catch (ContraseniaInvalidaException e) {
    		final String mensaje = "La contrasenia es invalida porque: es muy debil";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
    
    @Test
    public void passwordComunOk(){
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaEsComun());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
        creadorDeUsuarios.crearUsuario("juan", "Pasmds1375fdfdf7", TipoUsuario.ADMINISTRADOR);
    }

    @Test
    public void passwordInvalidoPorSerCorta() {
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaLongitudApropiada());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
    	try {
            creadorDeUsuarios.crearUsuario("juan", "pDsd", TipoUsuario.ADMINISTRADOR);
    	} catch (ContraseniaInvalidaException e) {
    		final String mensaje = "La contrasenia es invalida porque: es muy corta";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
    
    @Test
    public void passwordLongitudOk(){
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaLongitudApropiada());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
        creadorDeUsuarios.crearUsuario("juan", "Pasdsdsdfd", TipoUsuario.ADMINISTRADOR);
    }
    
    @Test
    public void passwordInvalidoPorNoTenerMayusculas() {
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaContieneMayus());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
    	try {
            creadorDeUsuarios.crearUsuario("juan", "pasdsdsdsdsds", TipoUsuario.ADMINISTRADOR);
    	} catch (ContraseniaInvalidaException e) {
    		final String mensaje = "La contrasenia es invalida porque: no contiene ninguna mayuscula";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }

    @Test
    public void passwordMayusculasOk(){
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaContieneMayus());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
        creadorDeUsuarios.crearUsuario("juan", "Pasdsdsdf", TipoUsuario.ADMINISTRADOR);
    }

    @Test
    public void passwordInvalidoPorTenerUnCaracterConsecutivoIdentico() {
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaCaracteresConsecutivosIdenticos());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
    	try {
            creadorDeUsuarios.crearUsuario("juan", "Passds1234", TipoUsuario.ADMINISTRADOR);
    	} catch (ContraseniaInvalidaException e) {
    		final String mensaje = "La contrasenia es invalida porque: tiene caracteres consecutivos identicos";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
    
    @Test
    public void passwordConsecutivoIdenticoOk(){
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaCaracteresConsecutivosIdenticos());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
        creadorDeUsuarios.crearUsuario("juan", "Pasdsdsdf", TipoUsuario.ADMINISTRADOR);
    }

    @Test
    public void passwordInvalidoPorTenerUnCaracterConsecutivo() {
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaCaracteresConsecutivos());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
    	try {
            creadorDeUsuarios.crearUsuario("juan", "Pasmds1275", TipoUsuario.ADMINISTRADOR);
    	} catch (ContraseniaInvalidaException e) {
    		final String mensaje = "La contrasenia es invalida porque: tiene caracteres consecutivos";
    	    Assert.assertEquals(mensaje, e.getMessage());
    	}
    }
    
    @Test
    public void passwordConsecutivoOk(){
        creadorDeUsuarios = new CreadorDeUsuarios();
        listaValidaciones.add(new ContraseniaCaracteresConsecutivos());
        creadorDeUsuarios.setListaValidaciones(listaValidaciones);
        creadorDeUsuarios.crearUsuario("juan", "Pasmds13757", TipoUsuario.ADMINISTRADOR);
    }
}
