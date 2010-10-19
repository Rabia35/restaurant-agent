package sql;

import java.sql.ResultSet;

import util.*;

public class Peticion 
{
	public static Ingrediente obtenerPeticion()
	{
		Ingrediente ing = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery("select * from peticion order by hora asc");						
			
			if(rs.next())
			{
				ing = Ingrediente.obtenerIngrediente(rs.getString("ingrediente"));
				bd.ejectuarDML("delete from peticion where ingrediente = " + NoSQLInjection.comillas(rs.getString("ingrediente")));
			}
			
			bd.desconectar();
		}catch(Exception e)
		{			
			e.printStackTrace();
		}
		
		return ing;
	}

	public static void agregarPeticion (Ingrediente ing)
	{		
		BaseDeDatos bd = new BaseDeDatos();				
		bd.ejectuarDML(" insert into peticion (ingrediente, hora) values (" + NoSQLInjection.comillas(ing.clave) + ", now())");
	}
	
}
