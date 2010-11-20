package agentes;

import java.util.TreeMap;

import sql.Menu;
import util.AdministradorDF;
import util.Debug;
import comportamientos.BuscaAgentesParaAdministrador;
import jade.core.AID;
import jade.core.Agent;

public class AgenteAdministrador extends Agent 
{
	private static final long serialVersionUID = 4122529638717525303L;
	
	public final static int totalAgentesMenu = 3;
	
	public Menu menu;
	public AID[] agentesMenu;
	
	public TreeMap<String, Integer> map;
	
	@Override
	protected void setup() 
	{		
		map = new TreeMap<String, Integer>();
		addBehaviour(new BuscaAgentesParaAdministrador(this));
		AdministradorDF.daDeAlta(this, "AgenteAdministrador", "AgenteAdministrador");	
		agentesMenu = null;
		Debug.print("Administrador creado.");
	}	
	
	public void añadeReceta(String receta, int valor)
	{
		synchronized(AgenteAdministrador.class)
		{
			if (map.containsKey(receta))
				map.put(receta, valor + map.get(receta));
			else
				map.put(receta, valor);
		}
	}
}
