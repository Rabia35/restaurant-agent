package sql;

import java.sql.ResultSet;
import util.NoSQLInjection;

public class Receta 
{
	public static int MAX_INGREDIENTES = 4;
	
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
