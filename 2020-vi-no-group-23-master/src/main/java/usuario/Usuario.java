package usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import inicioSesion.TipoUsuario;

@Entity @Table(name="usuarios")
public class Usuario {
	
	@Id @GeneratedValue
	private Long id;
    private String nombre;
    private String contrasenia;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
    @ManyToMany @JoinTable(name="mensajes_por_usuarios")
    public Collection<Mensaje> mensajes = new ArrayList<>();
    
    public Usuario(String nombre, String contrasenia, TipoUsuario tipo){
    	this.nombre = nombre;
    	this.contrasenia = contrasenia;
    	this.tipo = tipo;
    }
    
	public Usuario() {
		super();
	}

	public String getNombre() {
		return nombre;
	}
	
	public Long getId() {
		return id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}


	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}
}
