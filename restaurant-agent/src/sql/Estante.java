package sql;

import java.sql.ResultSet;
import util.*;

public class Estante 
{
	public int posicionX;
	public int posicionY;
	public int altura;
	public boolean refrigerador;		
	
	public Ingrediente ingrediente;
	
	public static Estante obtenerEstante(int x, int y, int altura)
	{
		Estante est = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select * from estante where posicionX = " + x +
					                        " and posicionY = " + y +
					                        " and altura = " + altura);
			rs.next();
			
			est = new Estante();					
			
			est.posicionX = x;
			est.posicionY = y;
			est.altura = altura;
			est.refrigerador = rs.getBoolean("refrigerador");
			est.ingrediente = Ingrediente.obtenerIngrediente(rs.getString("ingrediente"));
			
			if (est.ingrediente != null)
			{			
				est.ingrediente.fechaColocado = rs.getTimestamp("fechaColocado");
				est.ingrediente.cantidad = rs.getInt("cantidad");
			}
			
			bd.desconectar();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return est;
	}
	
	public Ingrediente retirarIngrediente(int cantidad)
	{
		if(ingrediente == null)
			return null;
		
		if(cantidad > ingrediente.cantidad)
			cantidad = ingrediente.cantidad;
		
		Ingrediente sacado = new Ingrediente();
		
		sacado.clave = ingrediente.clave;
		sacado.nombre = ingrediente.nombre;
		sacado.costo = ingrediente.costo;
		sacado.peso = ingrediente.peso;
		sacado.refrigerado = ingrediente.refrigerado;			
		sacado.caducidad = ingrediente.caducidad;
		sacado.cantidadPorPaquete = ingrediente.cantidadPorPaquete;
		
		sacado.cantidad = cantidad;
		sacado.fechaColocado = ingrediente.fechaColocado;
		
		ingrediente.cantidad -= cantidad;
		
		BaseDeDatos bd = new BaseDeDatos();				
		
		if (ingrediente.cantidad == 0)
		{
			bd.ejectuarDML(" update estante set ingrediente = null, cantidad = 0, fechaColocado = null" +
					         " where posicionX = " + posicionX + 
					         " and posicionY = " +  posicionY + 
					         " and altura = " + altura);
			ingrediente = null;
		}
		else
			bd.ejectuarDML(" update estante set cantidad = " + ingrediente.cantidad + 
			         " where posicionX = " + posicionX + 
			         " and posicionY = " +  posicionY + 
			         " and altura = " + altura);
		
		return sacado;
	}
	
	public void ponerIngrediente(Ingrediente ing)
	{		
		if(ingrediente != null)
			return;
		
		ingrediente = ing;

		BaseDeDatos bd = new BaseDeDatos();						
		
		bd.ejectuarDML(" update estante set " + 
				         " ingrediente = "  + NoSQLInjection.comillas(ing.clave) + " , " + 
				         " cantidad = " + ing.cantidad + ", " + 
				         " fechaColocado = now()" +
				         " where posicionX = " + posicionX + 
				         " and posicionY = " +  posicionY + 
				         " and altura = " + altura);
	}
	
	@Override
	public String toString() {
		String s = 
			  "posicionX = " + posicionX +
			"\nposicionY = " + posicionY +
			"\naltura = " + altura +
			"\nrefrigerador = " + refrigerador +
			"\ningrediente = " + (ingrediente == null ? null : ingrediente.nombre);			
		return s;
	}

}
