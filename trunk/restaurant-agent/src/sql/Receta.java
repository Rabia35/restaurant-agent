package sql;

import java.sql.ResultSet;

import util.NoSQLInjection;

public class Receta 
{
	public static int MAX_INGREDIENTES = 4;
	
	private static int CONSTANTE_SALUDABLE = 20;
	private static int CONSTANTE_CADUCA = 60;
	private static int INGREDIENTES_PRINCIPALES = 2;
	
	public static int VALOR_RECETA_COMPLETA = 100;
	public static int CASTIGO_INGREDIENTE_FALTANTE = 30;
	
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
		for (int i = 0; i < INGREDIENTES_PRINCIPALES; i++)
			if (!ingredientes[i].hayEnAlmacenSuficientes(cantidadIngrediente[i]))
				return ingredientes[i];
		return null;
	}
	
	public int valorDeServirReceta()
	{
		int valor = VALOR_RECETA_COMPLETA;
		
		if (ingredienteFaltante() != null)
			return 0;
		
		for (int i = INGREDIENTES_PRINCIPALES; i < ingredientes.length; i++)
			if (!ingredientes[i].hayEnAlmacenSuficientes(cantidadIngrediente[i]))
				valor -= CASTIGO_INGREDIENTE_FALTANTE;
		
		return valor;
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

	private static String[][] obtenerRecetas (String query)
	{
		String[][] recetas = null;
		try {			
			BaseDeDatos bd = new BaseDeDatos();			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(query);
			
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
	
	public static String[][] obtenerRecetasCaras()
	{		
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
			
		return obtenerRecetas(s);
	}
	
	public static String[][] obtenerRecetasSaludables()
	{
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
		
		return obtenerRecetas(s);
	}
	
	public static String[][] obtenerRecetasPrudentes()
	{
		String subquery = 
			"(select min(i.caducidad * " + CONSTANTE_CADUCA + 
			" - (now() - fechaColocado)) as total, e.ingrediente from estante " +
			" as e inner join ingrediente as i on i.clave = e.ingrediente " + 
			"group by e.ingrediente order by total asc)";
		
		String s = " select * from ((select r.clave, ((500 * " + CONSTANTE_CADUCA + 
		  " - t1.total) / (25 * " + CONSTANTE_CADUCA + ")) + ((500 * " + CONSTANTE_CADUCA + 
		  " - t2.total) / (25 * " + CONSTANTE_CADUCA + ")) + ((500 * " + CONSTANTE_CADUCA + 
		  " - t3.total) / (25 * " + CONSTANTE_CADUCA + ")) as total from receta as r " + 
		  " inner join " + subquery + " as t1 on r.ingrediente1 = t1.ingrediente " + 
		  " inner join " + subquery + " as t2 on r.ingrediente2 = t2.ingrediente " + 
		  " inner join " + subquery + " as t3 on r.ingrediente3 = t3.ingrediente) union" + 
		  " (select r.clave, ((500 * " + CONSTANTE_CADUCA + 
		  " - t1.total) / (25 * " + CONSTANTE_CADUCA + ")) + ((500 * " + CONSTANTE_CADUCA + 
		  " - t2.total) / (25 * " + CONSTANTE_CADUCA + ")) + ((500 * " + CONSTANTE_CADUCA + 
		  " - t3.total) / (25 * " + CONSTANTE_CADUCA + ")) + ((500 * " + CONSTANTE_CADUCA + 
		  " - t4.total) / (25 * " + CONSTANTE_CADUCA + ")) as total from receta as r " + 
		  " inner join " + subquery + " as t1 on r.ingrediente1 = t1.ingrediente " + 
		  " inner join " + subquery + " as t2 on r.ingrediente2 = t2.ingrediente " + 
		  " inner join " + subquery + " as t3 on r.ingrediente3 = t3.ingrediente " +
		  " inner join " + subquery + " as t4 on r.ingrediente4 = t4.ingrediente)) as tmp";		
		
		return obtenerRecetas(s);
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
