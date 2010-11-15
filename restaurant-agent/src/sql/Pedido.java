package sql;

import java.sql.ResultSet;

import util.*;

public class Pedido 
{
	public static Receta[] obtenerPlatillos(int cuantos, int aPartirDeCuantos)
	{
		Receta platillos[] = new Receta[cuantos];
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery("select * from pedido order by tiempoPedido desc");						
			
			int i;
			for(i = 0; i < aPartirDeCuantos; i++)
				if(!rs.next())
					break;
			
			if(i == aPartirDeCuantos)
			{
				int j;
				for(j = 0; j < cuantos; j++)
				{
					platillos[j] = Receta.obtenerReceta(rs.getString("receta"));
					if(!rs.next())
						break;
				}					
			}
				 			
			bd.desconectar();
		}catch(Exception e)
		{			
			e.printStackTrace();
		}
		
		return platillos;
	}

	public static void agregarPlatillo(Receta rec)
	{		
		BaseDeDatos bd = new BaseDeDatos();				
		bd.ejectuarDML(" insert into pedido (receta, tiempoPedido) values (" + NoSQLInjection.comillas(rec.clave) + ", now())");
	}
	
}
