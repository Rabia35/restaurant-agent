package comportamientos;

import java.util.Random;

import sql.Menu;
import sql.Pedido;
import sql.Receta;

import agentes.AgenteChef;
import jade.core.behaviours.TickerBehaviour;

public class PidePlatillo extends TickerBehaviour 
{
	private static final long serialVersionUID = 3325363131790539744L;
	private static final long TIEMPO_ESPERA = 5000L;
	
	private static final long probabilidadPedir = 3; //de 10;
	
	private AgenteChef chef;	

	public PidePlatillo(AgenteChef a) {
		super(a, TIEMPO_ESPERA);
		chef = a;
	}

	
	
	@Override
	protected void onTick() 
	{
		
		if(chef.menu.recetas.length>0){
			Random r = new Random();
		
			if (r.nextInt(10) < probabilidadPedir)
				pideReceta();
		}
		
	}
	
	private void pideReceta()
	{
		Random r = new Random();
		
		chef.menu = Menu.obtenerMenu();
		
		int cual = r.nextInt(chef.menu.recetas.length);
		
		pideIngredientes(chef.menu.recetas[cual]);
	}
	
	private void pideIngredientes(Receta cual)
	{
		Pedido.agregarPlatillo(cual);
		
		for (int i = 0; i < cual.ingredientes.length; i++)
			chef.negociaIngrediente(cual.ingredientes[i], cual.cantidadIngrediente[i]);
	}
}
