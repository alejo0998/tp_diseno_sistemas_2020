package repositorios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import atributos.Moneda;
import categoria.Categoria;
import compras.Compra;
import etiqueta.Etiqueta;
import item.Item;
import usuario.Usuario;

public class RepositorioItems implements WithGlobalEntityManager{
	
	public static RepositorioItems instancia = new RepositorioItems();
	
	public List<Moneda> listarMonedas() {
		return entityManager()
				.createQuery("from Moneda", Moneda.class)
				.getResultList();
	}
	
	public List<Item> listar() {
		return entityManager()
				.createQuery("from Item", Item.class)
				.getResultList();
	}
	
	
	public Moneda buscar(long id) {
	    return entityManager().find(Moneda.class, id);
	}
	
	public void agregarItem(long idItem, Compra compra) {
		Item item = RepositorioItems.instancia.buscarItem(idItem);
		compra.agregarItem(item);
	}
	
	public Item buscarItem(long id) {
	    return entityManager().find(Item.class, id);
	}
	

}