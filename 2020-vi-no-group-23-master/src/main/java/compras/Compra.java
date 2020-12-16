package compras;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import etiqueta.Etiqueta;
import excepciones.CompraInvalidaException;
import item.Item;
import pagos.MedioDePago;
import presupuestos.Presupuesto;
import usuario.Mensaje;
import usuario.Usuario;


@Entity
@Table(name="compras")
public class Compra {
	
	@Id @GeneratedValue
	private Long id;
	@OneToMany
	@JoinColumn(name="items")
	private Collection<Item> items;
	@OneToOne @JoinColumn(name="medio_de_pago")
	private MedioDePago medioDePago;
	@ManyToOne @JoinTable(name="proveedores_por_compras")
	private Proveedor proveedor;
	@Column(name="cant_presupuestos")
	private int cantidadPresupuestos;
	@ManyToMany @JoinTable(name="revisores_por_compras")
	private Collection<Usuario> revisoresCompra = new ArrayList<>();
	@Enumerated(EnumType.STRING) @Column(name="estado_validacion")
	private EstadoValidacion estadoValidacion;
	@OneToMany
	@JoinColumn(name="presupuestos")
	private Collection<Presupuesto> presupuestos = new ArrayList<>();
	@ManyToMany @JoinTable(name="etiquetas_por_compras")
	private Collection<Etiqueta> etiquetas = new ArrayList<>();
	private LocalDate fecha = LocalDate.now();
	//private EntidadBase entidadBase;
	
	//private EntidadJuridica entidadJuridica;
	
	public Compra(Collection<Item> items, MedioDePago medioDePago, Proveedor proveedor, int cantPresupuestos, Collection<Usuario> revisores){
		//		if (items.size() == 0) {
//			throw new CompraInvalidaException("no contiene items");
//		} 
		if (medioDePago == null) {
			throw new CompraInvalidaException("no contiene medio de pago");
		} if (proveedor == null) {
			throw new CompraInvalidaException("no contiene un vendedor");
		}
		this.items = items;
		this.medioDePago = medioDePago;
		this.proveedor = proveedor;
		this.cantidadPresupuestos = cantPresupuestos;
		this.revisoresCompra = revisores;
		this.estadoValidacion = EstadoValidacion.PENDIENTE_EVALUACION;
	}
	
	public Compra() {
		super();
	}

	public double calcularValorCompra() {
		return items.stream().mapToDouble(Item::getValorTotal).sum(); //POR AHORA NO UTILIZO EL MEDIO DE PAGO (ejemplo)
	}

	public Collection<Usuario> revisorCompra() {
		return revisoresCompra;
	}

	public Collection<Item> getItems() {
		return items;
	}

	public boolean requierePresupuesto() {
		return (this.cantidadPresupuestos > 0);
	}

	//DocumentoComercial generarComprobante(){}

	public void agregarRevisor(Usuario revisor) {
		revisoresCompra.add(revisor);
	}
	
	public void agregarItem(Item item) {
		items.add(item);
	}
	
	public void setEstadoValidacion(EstadoValidacion estadoValidacion) {
		this.estadoValidacion = estadoValidacion;
	}

	public EstadoValidacion getEstadoValidacion() {
		return estadoValidacion;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public MedioDePago getMedioDePago() {
		return this.medioDePago;
	}
	
	public Collection<Usuario> getRevisores() {
		return this.revisoresCompra;
	}
	
	public Collection<Etiqueta> getEtiquetas() {
		return this.etiquetas;
	}
	
	public LocalDate getDate() {
		return this.fecha;
	}
	
	public void notificarRevisores(String mensajeValidacion) {
		Mensaje mensaje = new Mensaje(this.proveedor,this.estadoValidacion,this.fecha, mensajeValidacion);
		for (Usuario usuario : revisoresCompra) {
			usuario.getMensajes().add(mensaje);
		}
	}
	
	public void agregarEtiqueta(Etiqueta etiqueta) {
		etiquetas.add(etiqueta);
	}
	
	public Collection<Etiqueta> obtenerEtiquetas() {
		return this.etiquetas;
	}

	public Collection<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void agregarPresupuesto(Presupuesto presupuesto) {
		//chequeo si es valido el presupuesto
		for (Item itemPresupuesto : presupuesto.items) {
			if (this.items.stream().anyMatch((Item itemCompra) -> ((itemCompra.getTipoItem() == itemPresupuesto.getTipoItem()) && (itemCompra.getCantidad() == itemPresupuesto.getCantidad()) && (itemCompra.getDescripcion().equals(itemPresupuesto.getDescripcion()))))) {
				continue;
			}
			throw new RuntimeException();
		}
		if (this.items.size() != presupuesto.items.size()) {
			throw new RuntimeException();
		}
		this.presupuestos.add(presupuesto);
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setRevisoresCompra(Collection<Usuario> revisoresCompra) {
		this.revisoresCompra = revisoresCompra;
	}
	
	public int getCantidadPresupuestos() {
		return this.cantidadPresupuestos;
	}
}
