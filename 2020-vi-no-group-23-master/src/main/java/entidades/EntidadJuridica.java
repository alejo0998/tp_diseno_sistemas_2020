package entidades;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import compras.Compra;
import excepciones.CompraInvalidaException;
import excepciones.EntidadException;
import reglasNegocio.TipoReglaNegocio;

@Entity @Table(name="entidades_juridicas")
public class EntidadJuridica extends Entidad{

	@Id @GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private CategoriaEntidad categoriaEntidad;
	@Column(name="nombre_ficticio")
	private String nombreFicticio;
	@Column(name="razon_social")
	private String razonSocial;
	private long cuit;
	@Column(name="codigo_postal")
	private int codigoPostal;
	@OneToMany
	@JoinColumn(name="compras")
	private Collection<Compra> compras;
	@OneToMany
	@JoinColumn(name="entidad_base")
	private Collection<EntidadBase> entidadesBase;
	@ManyToOne
	private Organizacion organizacion;
	
	public EntidadJuridica(String nombreFicticio, String razonSocial, long cuit, int codigoPostal, Collection<EntidadBase> entidadesBase, CategoriaEntidad categoriaEntidad){
		this.nombreFicticio = nombreFicticio;
		this.razonSocial = razonSocial;
		this.cuit = cuit;
		this.codigoPostal = codigoPostal;
		this.entidadesBase = entidadesBase;
		this.categoriaEntidad = categoriaEntidad;
	}
	
	public EntidadJuridica() {
		super();
	}
	
	public void agregarEntidadAsociada(EntidadBase entidadBase) {
//		if (puedeAceptarNuevaEntidadBase()) {
//			if (entidadBase.puedeSerParteDeEntidadJuridica()) {
				entidadesBase.add(entidadBase);
//			} else {
//				throw new EntidadException("La entidad base no puede ser parte de una entidad jurídica");
//			}
//		} else {
//			throw new EntidadException("Tiene una categoría que no permite agregar una entidad base");
//		}
	}
	
	public void asociarOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	public void agregarCompra(Compra compra) {
		if(!compras.contains(compra)) {
			compras.add(compra);
		}else {
			throw new CompraInvalidaException("La compra ya es parte de las compras ");
		}
		
	}
	
	public boolean puedeAceptarNuevaEntidadBase() {
		if (categoria != null) {
			return !categoria.cumpleConRegla(TipoReglaNegocio.NO_AGREGAR_ENTIDAD_BASE, this);
		}
		return true;
	}
	
	public boolean contieneEntidadBase(EntidadBase entidad) {
		return this.entidadesBase.contains(entidad);
	}
	
	public String getNombreFicticio() {
		return nombreFicticio;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public long getCuit() {
		return cuit;
	}
	
	public Long getId() {
		return id;
	}
	
	public int getCodigoPostal() {
		return codigoPostal;
	}

}
