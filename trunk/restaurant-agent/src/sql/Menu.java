package sql;

import java.sql.ResultSet;
import util.*;

public class Menu 
{
	public Receta recetas[];
	
	public static Menu obtenerMenu()
	{
		Menu men = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery("select * from menu");						
			men = new Menu();
			
			rs.last();
			men.recetas = new Receta[rs.getRow()];
			rs.beforeFirst();
			
			int i = 0;			
			while(rs.next())
				men.recetas[i++] = Receta.obtenerReceta(rs.getString("receta"));
			
			bd.desconectar();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return men;
	}

	public void quitaReceta(Receta rec)
	{
		Receta temp[] = new Receta[recetas.length - 1];
		
		for (int i = 0, j = 0; i < recetas.length; i++, j++)
			if (recetas[i].clave.equals(rec.clave))
				i++;
			else
				temp[j] = recetas[i];

		recetas = temp;
		
		BaseDeDatos bd = new BaseDeDatos();				
		bd.ejectuarDML(" delete from menu where receta = " + NoSQLInjection.comillas(rec.clave));
	}
	
	public void agregaReceta(Receta rec)
	{
		int pos = recetas.length;
		Receta temp[] = new Receta[pos + 1];
		
		for (int i = 0; i < pos; i++)
			temp[i] = recetas[i];

		recetas = temp;
		
		recetas[pos] = rec;
		
		BaseDeDatos bd = new BaseDeDatos();				
		bd.ejectuarDML(" insert into menu (receta) values (" + NoSQLInjection.comillas(rec.clave) + ")");
	}

	public Receta recetaImposibleDeServir()
	{
		for(int i = 0; i < recetas.length; i++)
			if (recetas[i].ingredienteFaltante() != null)
				return recetas[i];
		return null;
	}
	
	@Override
	public String toString() {
		String s = "Menu:\n\n\n";
		for(int i = 0; i < recetas.length; i++)
			s += recetas[i] + "\n\n\n";
		return s;
	}
}
