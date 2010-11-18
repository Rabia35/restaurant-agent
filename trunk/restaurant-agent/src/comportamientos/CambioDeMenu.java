package comportamientos;

import agentes.AgenteAdministrador;
import jade.core.behaviours.TickerBehaviour;
import sql.Menu;
import sql.Receta;
import util.*;

public class CambioDeMenu extends TickerBehaviour 
{
	private static final long serialVersionUID = 19836069642149489L;
	private static final long tiempoDeRevision = 10000;	
	
	private AgenteAdministrador miAgente;

	public CambioDeMenu(AgenteAdministrador a) 
	{		
		super(a, tiempoDeRevision);
		miAgente = a;
	}

	@Override
	protected void onTick() 
	{
		miAgente.menu = Menu.obtenerMenu();
		Receta receta = miAgente.menu.recetaImposibleDeServir();
		
		if (receta == null)
			return;
		
		Debug.print("Cambio de receta " + receta.nombre); //DEBUG
		
		receta.resurtirReceta();
		Mensaje.mandaMensaje(miAgente, Performativas.CAMBIAR, miAgente.agentesMenu, receta.clave);
		
		int numAgentes = AgenteAdministrador.totalAgentesMenu;
		
		miAgente.propuestas = new String[numAgentes][numAgentes*numAgentes];
		miAgente.valores = new int[numAgentes][numAgentes*numAgentes];
		
		miAgente.addBehaviour(new RecibirPropuestasMenu(miAgente, 1));
		this.stop();		
	}
}
