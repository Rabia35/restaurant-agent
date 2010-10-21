package util;

import sql.Ingrediente;

public class Pedido{
	private Ingrediente ingrediente;
	private int cantidad;
	
	public Pedido(String cveIngrediente, int cantidad){
		ingrediente = Ingrediente.obtenerIngrediente(cveIngrediente);
		this.cantidad = cantidad;
	}
	
	public Ingrediente getIngrediente(){
		return ingrediente;
	}
	
	public int getCantidad(){
		return cantidad;
	}
}