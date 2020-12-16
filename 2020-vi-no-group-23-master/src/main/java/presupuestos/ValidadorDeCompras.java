package presupuestos;

import java.util.ArrayList;
import java.util.List;
import compras.Compra;

public class ValidadorDeCompras {

	private List<ValidacionCompra> listaValidaciones = new ArrayList<>();

	public void validarCompra(Compra compra) {
		for (ValidacionCompra validacion : listaValidaciones) {
			validacion.validacionCompra(compra);
		}
	}

	public void setListaValidaciones(List<ValidacionCompra> listaValidaciones) {
		this.listaValidaciones = listaValidaciones;
	}

}