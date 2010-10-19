package sql;

import java.sql.ResultSet;
import java.sql.Timestamp;

import util.*;

public class Ingrediente 
{
	public String clave;
	public String nombre;
	public int costo;
	public int peso;
	public boolean refrigerado;
	public int caducidad;
	public int cantidadPorPaquete;
	
	public Timestamp fechaColocado;
	public int cantidad;
	
	public static Ingrediente obtenerIngrediente(String clave)
	{
		if (clave == null || clave.equals("0"))
			return null;	
		
		Ingrediente ing = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select * from ingrediente where clave = " +
					                        NoSQLInjection.comillas(clave));
			rs.next();
			
			ing = new Ingrediente();
			
			ing.clave = clave;
			ing.nombre = rs.getString("nombre");
			ing.costo = rs.getInt("costo");
			ing.peso = rs.getInt("peso");
			ing.refrigerado = rs.getBoolean("refrigerado");			
			ing.caducidad = rs.getInt("caducidad");
			ing.cantidadPorPaquete = rs.getInt("cantidadPorPaquete");			
			
			bd.desconectar();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return ing;
	}
	
	public boolean hayEnAlmacenSuficientes()
	{
		try
		{
			BaseDeDatos bd = new BaseDeDatos();			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select sum(cantidad) as suma from estante where ingrediente = " +
					                          NoSQLInjection.comillas(clave));
			rs.next();			
			Integer suma = rs.getInt("suma"); 						
			bd.desconectar();
			
			if (suma == null)
				return false;
			
			return (suma >= cantidad);			
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return false;
	}
	
	@Override
 	public String toString() {
		String s = 
			  "clave = " + clave + 
			"\nnombre = " + nombre + 
			"\ncosto = "+ costo + 
			"\npeso = " + peso +
			"\nrefrigerado = " + refrigerado +			
			"\ncaducidad = " + caducidad +
			"\ncantidadPorPaquete = " + cantidadPorPaquete +
			"\nfechaColocado = " + fechaColocado +
			"\ncantidad = " + cantidad;
		return s;
	}
}
