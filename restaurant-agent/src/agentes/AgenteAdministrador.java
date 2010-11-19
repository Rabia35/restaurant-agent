package agentes;

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
	
	public String[][] propuestas;
	public int[][] valores;
	
	@Override
	protected void setup() 
	{		
		addBehaviour(new BuscaAgentesParaAdministrador(this));
		AdministradorDF.daDeAlta(this, "AgenteAdministrador", "AgenteAdministrador");	
		agentesMenu = null;
		Debug.print("Administrador creado.");
	}	
}
