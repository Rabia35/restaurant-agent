package sql;

import java.sql.ResultSet;
import util.NoSQLInjection;

public class Receta 
{
	public static int MAX_INGREDIENTES = 4;
	
	private static int CONSTANTE_SALUDABLE = 20;
	
	public String clave;
	public String nombre;
	
	public Ingrediente ingredientes[];
	public int cantidadIngrediente[];
	
	public static Receta obtenerReceta(String clave)
	{
		Receta rec = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select * from receta where clave = " + 
					                        NoSQLInjection.comillas(clave));
			rs.next();
			
			rec = new Receta();					
			
			rec.clave = clave;
			rec.nombre = rs.getString("nombre");
			
			int totalIngredientes;
			for(totalIngredientes = MAX_INGREDIENTES; totalIngredientes > 0; totalIngredientes--)
				if(rs.getString("ingrediente" + totalIngredientes) != null)
					break;
			
			rec.ingredientes = new Ingrediente[totalIngredientes];
			rec.cantidadIngrediente = new int[totalIngredientes];
			
			for(int i = 0; i < totalIngredientes; i++)
			{
				rec.ingredientes[i] = new Ingrediente();
				
				rec.ingredientes[i].clave = rs.getString("ingrediente" + (i + 1));
				rec.cantidadIngrediente[i] = rs.getInt("cantidad" + (i + 1));
			}

			bd.desconectar();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return rec;
	}
	
	public Ingrediente ingredienteFaltante()
	{
		for (int i = 0; i < ingredientes.length; i++)
			if (!ingredientes[i].hayEnAlmacenSuficientes(cantidadIngrediente[i]))
				return ingredientes[i];
		return null;
	}
	
	public void resurtirReceta()
	{
		for (int i = 0; i < ingredientes.length; i++)
			if (!ingredientes[i].hayEnAlmacenSuficientes(cantidadIngrediente[i]))
				Peticion.agregarPeticion(ingredientes[i]);
	}
	
	private static String recetasPosibles()
	{
		String res = "";
		try 
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			String s = 	 
				" select clave from receta where cantidad1 <= (select sum(cantidad) from estante where ingrediente = ingrediente1) " + 
				" and cantidad2 <= (select sum(cantidad) from estante where ingrediente = ingrediente2) " +
				" and cantidad3 <= (select sum(cantidad) from estante where ingrediente = ingrediente3) " + 
				" and (ingrediente4 is NULL or cantidad4 <= (select sum(cantidad) from estante where ingrediente = ingrediente4))";
			
			bd.conectar();
			ResultSet r = bd.realizarQuery(s);
			
			res = "(";
			
			boolean sig = r.next();
			
			while(sig)
			{
				res += "'" + r.getString("clave") + "'";
				sig = r.next();
				if (sig)
					res += ",";
			}
		
			res += ")";
			bd.desconectar();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	public static String[][] obtenerRecetasCaras()
	{
		String[][] recetas = null;
		try {
			String validas = recetasPosibles();
			
			String s = " select * from (" + 
			 " (select (i1.costo + i2.costo + i3.costo + i4.costo) as total, r.clave from receta as r " + 
			 " inner join ingrediente as i1 on i1.clave = r.ingrediente1 " + 
			 " inner join ingrediente as i2 on i2.clave = r.ingrediente2 " + 
			 " inner join ingrediente as i3 on i3.clave = r.ingrediente3 " + 
			 " inner join ingrediente as i4 on i4.clave = r.ingrediente4) union " + 
			 " (select (i1.costo + i2.costo + i3.costo) as total, r.clave from receta as r " + 
			 " inner join ingrediente as i1 on i1.clave = r.ingrediente1 " +
			 " inner join ingrediente as i2 on i2.clave = r.ingrediente2 " + 
			 " inner join ingrediente as i3 on i3.clave = r.ingrediente3)) as tmp " + 
			 " where clave in " + validas + 
			 " order by total desc";
			
			BaseDeDatos bd = new BaseDeDatos();			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(s);
			
			rs.last();
			recetas = new String[rs.getRow()][2];
			rs.beforeFirst();
			
			int i = 0;
			while(rs.next())
			{
				recetas[i][0] = rs.getString("clave");
				recetas[i][1] = rs.getString("total");
				i++;
			}
			
			bd.desconectar();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return recetas; 
	}
	
	public static String[][] obtenerRecetasSaludables()
	{
		String[][] recetas = null;
		try {
			String validas = recetasPosibles();
			
			String s = " select * from (" + 
			 " (select (i1.saludable + i2.saludable + i3.saludable + i4.saludable) * " + CONSTANTE_SALUDABLE +
			 " as total, r.clave from receta as r " + 
			 " inner join ingrediente as i1 on i1.clave = r.ingrediente1 " + 
			 " inner join ingrediente as i2 on i2.clave = r.ingrediente2 " + 
			 " inner join ingrediente as i3 on i3.clave = r.ingrediente3 " + 
			 " inner join ingrediente as i4 on i4.clave = r.ingrediente4) union " + 
			 " (select (i1.saludable + i2.saludable + i3.saludable) * " + CONSTANTE_SALUDABLE +
			 " as total, r.clave from receta as r " + 
			 " inner join ingrediente as i1 on i1.clave = r.ingrediente1 " +
			 " inner join ingrediente as i2 on i2.clave = r.ingrediente2 " + 
			 " inner join ingrediente as i3 on i3.clave = r.ingrediente3)) as tmp " + 
			 " where clave in " + validas + 
			 " order by total desc";
			
			BaseDeDatos bd = new BaseDeDatos();			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(s);
			
			rs.last();
			recetas = new String[rs.getRow()][2];
			rs.beforeFirst();
			
			int i = 0;
			while(rs.next())
			{
				recetas[i][0] = rs.getString("clave");
				recetas[i][1] = rs.getString("total");
				i++;
			}
			
			bd.desconectar();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return recetas; 
	}
	
	@Override
	public String toString() 
	{
		String s = 
			  "clave = " + clave + 
			"\nnombre = " + nombre;
		
		for(int i = 0; i < ingredientes.length; i++)
			s += "\n\n\nIngrediente " + (i + 1) + " X " + cantidadIngrediente[i] + " \n\n" + ingredientes[i];

		return s;
	}
}
