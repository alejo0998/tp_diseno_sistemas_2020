package entidades;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import etiqueta.Etiqueta;


@Entity @Table(name="organizaciones")
public class Organizacion {

	@Id @GeneratedValue
	private Long id;
	@JoinColumn(name="razon_social")
	String razonSocial;
	@OneToMany
	@JoinColumn(name="entidades_juridicas")
	private Collection<EntidadJuridica> entidadesJuridicas;
	//@OneToMany(mappedBy="organizacion")
	//private Collection<EntidadBase> entidadesBase;
	@OneToMany
	@JoinColumn(name="etiquetas_existentes")
	public Collection<Etiqueta> etiquetasExistentes;
	
	
	
	
	public Organizacion(String razonSocial, Collection<EntidadJuridica> entidadesJuridicas){
		this.razonSocial = razonSocial;
		this.entidadesJuridicas = entidadesJuridicas;
		//this.entidadesBase = entidadesBase;
	}
	
	public Organizacion() {
		super();
	}
	
	public void agregarEntidadJuridica(EntidadJuridica entidad) {
		entidad.asociarOrganizacion(this);
		entidadesJuridicas.add(entidad);
	}
	
	public void agregarEtiqueta(String etiqueta) {
		 new Etiqueta(etiqueta);
	}
	
	public double reporteSegun(Etiqueta etiqueta) {
		double egresos = 0;
		// TODO
		return egresos;
	}
	
	public String getRazonSocial() {
		return this.razonSocial;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void crear_etiqueta(String nombreEtiqueta){
		etiquetasExistentes.add(new Etiqueta(nombreEtiqueta));
	}
	
}

