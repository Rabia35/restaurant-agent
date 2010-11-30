package sql;

import java.sql.ResultSet;

import util.*;

public class Pedido 
{
	public Receta receta;
	public int valorReceta;
	
	public static Pedido[] obtenerPlatillos()
	{
		Pedido pedidos[] = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(
					" select * from pedido " +
					" where procesado = 0 " +
					" order by tiempoPedido desc");
			
			if (!rs.next())
				return null;
			
			rs.last();
			int cuantos = rs.getRow();
			rs.beforeFirst();			
			
			pedidos = new Pedido[cuantos];
				
			for(int j = 0; j < cuantos; j++)
			{
				rs.next();
				pedidos[j] = new Pedido();
				pedidos[j].valorReceta = rs.getInt("valorReceta");
				pedidos[j].receta = Receta.obtenerReceta(rs.getString("receta"));				
			}			
				
			bd.realizarQuery("update pedido set procesado = 1");
				 			
			bd.desconectar();
		}catch(Exception e)
		{			
			e.printStackTrace();
		}
		
		return pedidos;
	}

	public static void agregarPlatillo(Receta rec, int valorReceta)
	{		
		BaseDeDatos bd = new BaseDeDatos();				
		bd.ejectuarDML(" insert into pedido (receta, tiempoPedido, valorReceta) values (" + 
				NoSQLInjection.comillas(rec.clave) + ", now()" + valorReceta + ")");
	}
	
}
