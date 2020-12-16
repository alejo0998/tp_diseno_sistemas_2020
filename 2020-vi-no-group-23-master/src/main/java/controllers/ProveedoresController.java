package controllers;

import java.util.List;

import compras.Proveedor;
import repositorios.RepositorioProveedores;

public class ProveedoresController {

	public List<Proveedor> listar() {
		return RepositorioProveedores.instancia.listar();
	}

}
