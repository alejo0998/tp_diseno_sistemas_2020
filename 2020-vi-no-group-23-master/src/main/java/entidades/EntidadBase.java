package entidades;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import compras.Compra;
import etiqueta.Etiqueta;
import reglasNegocio.TipoReglaNegocio;


@Entity @Table(name="entidades_base")
public class EntidadBase extends Entidad{
	
	@Id @GeneratedValue
	private Long id;
	@Column(name="nombre_ficticio")
	private String nombreFicticio;
	private String descripcion;
	@ManyToOne
	private Organizacion organizacion;
	@OneToMany
	private Collection<Compra> compras;
	//@Column(name="entidad_juridica")
	//private EntidadJuridica entidadJuridica;
	
	public EntidadBase (String nombreFicticio, String descripcion, Organizacion organizacionAsociada){ 
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
		this.organizacion = organizacionAsociada;
	}
	
	public EntidadBase() {
		super();
	}
	
	public boolean puedeSerParteDeEntidadJuridica() {
		if (categoria != null) {
			return categoria.cumpleConRegla(TipoReglaNegocio.NO_SER_DE_ENTIDAD_JURIDICA, this);
		}
		return true;
	}
	
	public double gastosEgresos() {
		return this.compras.stream().mapToDouble( compra -> compra.calcularValorCompra() ).sum(); //POR AHORA NO UTILIZO EL MEDIO DE PAGO (ejemplo) 
	}

	public void asignarEtiqueta(Compra compra, Etiqueta etiqueta) {
		
		if(this.organizacion.etiquetasExistentes.contains(etiqueta)) {		
			compra.agregarEtiqueta(etiqueta);
		}
	}
	
	public double gastosEtiqueta(Etiqueta etiqueta) {
		// ??????????
		// TODO
		//return compras.stream().filter(compra -> compra.obtenerEtiquetas() == etiqueta.mapToDouble(compra -> compra.calcularValorCompra()).sum();
		return 0;
	}
	
	public String getNombreFicticio() {
		return nombreFicticio;
	}
	
	public void asignarOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public Long getId() {
		return id;
	}
}

