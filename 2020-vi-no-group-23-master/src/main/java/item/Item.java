package item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import atributos.Moneda;
import compras.Compra;

@Entity @Table(name="items")
public class Item {

	@Id @GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private TipoItem tipo;
	private String descripcion;
	private int cantidad;
	@Column(name="valor_unitario")
	private double valorUnitario;
	@ManyToOne
	private Moneda moneda;
	//@SuppressWarnings("unused")
	//private Compra compra;
	
	public Item(TipoItem tipo, String descripcion, double valorUnitario, int cantidad, Moneda moneda){
		this.cantidad = cantidad;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
		this.moneda = moneda;
	}
	
	public Item() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public double getValorTotal() {
		return valorUnitario * cantidad;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public TipoItem getTipoItem() {
		return this.tipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public double getValorUnitario() {
		return this.valorUnitario;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

}