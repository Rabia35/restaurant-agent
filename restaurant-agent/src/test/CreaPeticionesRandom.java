package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import sql.BaseDeDatos;
import sql.Ingrediente;
import sql.Peticion;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class CreaPeticionesRandom extends TickerBehaviour 
{
	private static final long serialVersionUID = 1963340658548057187L;
	
	private static final long tiempoDeRevision = 9000;
	private String[] ingredientes;
	private Random random;

	public CreaPeticionesRandom(Agent a) 
	{
		super(a, tiempoDeRevision);
		
		BaseDeDatos bd = new BaseDeDatos();
		bd.conectar();
		
		ResultSet res = bd.realizarQuery("select clave from ingrediente");
		
		try {
			res.last();
			ingredientes = new String[res.getRow()];
			res.beforeFirst();
			int i = 0;
			
			while(res.next())
				ingredientes[i++] = res.getString("clave");
		} catch (SQLException e) {			
			
		}		
		
		bd.desconectar();
		
		random = new Random();
	}
	
	@Override
	protected void onTick() 
	{
		int ing = random.nextInt(ingredientes.length);
		Peticion.agregarPeticion(Ingrediente.obtenerIngrediente(ingredientes[ing]));
	}

}
