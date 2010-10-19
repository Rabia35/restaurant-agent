package agentes;

import sql.Menu;
import util.AdministradorDF;
import comportamientos.BuscaAgentesParaAdministrador;
import comportamientos.CambioDeMenu;
import jade.core.AID;
import jade.core.Agent;

public class AgenteAdministrador extends Agent 
{
	private static final long serialVersionUID = 4122529638717525303L;	
	
	public Menu menu;
	public AID[] agentesMenu;
	
	@Override
	protected void setup() 
	{		
		addBehaviour(new BuscaAgentesParaAdministrador(this));
		addBehaviour(new CambioDeMenu(this));
		AdministradorDF.daDeAlta(this, "AgenteAdministrador", "AgenteAdministrador");	
		agentesMenu = null;
		System.out.println("Administrador creado.");
	}	
}
