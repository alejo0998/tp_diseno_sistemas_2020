package presupuestos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import compras.Proveedor;
import item.Item;

@Entity @Table(name="presupuestos")
public class Presupuesto {
	
	@Id @GeneratedValue
	private Long id;
	public double total;
	@ManyToMany @JoinTable(name="items_por_presupuestos")
	public List<Item> items;
	@ManyToOne
	public Proveedor proveedor;
	
	public Presupuesto(List<Item> productos, Proveedor proveedor) {
		this.proveedor = proveedor;
		this.items = productos;
		this.total = this.calcularValorCompra();
	}

	double obtenerTotal(){
		return this.total;
	}

	public double calcularValorCompra() {
		return items.stream().mapToDouble(Item::getValorTotal).sum(); //POR AHORA NO UTILIZO EL MEDIO DE PAGO (ejemplo)
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

}
