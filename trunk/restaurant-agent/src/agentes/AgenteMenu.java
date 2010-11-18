package agentes;

import comportamientos.BuscaAgentesParaMenu;

import util.AdministradorDF;
import util.TipoMenu;
import jade.core.AID;
import jade.core.Agent;

public abstract class AgenteMenu extends Agent
{
	private static final long serialVersionUID = -8641471475205022401L;
	private String recetas[];
	public TipoMenu tipo;
	
	public AID[] menus;
	public AID administrador;

	public void setup() 
	{ 		
		recetas = new String[AgenteAdministrador.totalAgentesMenu * AgenteAdministrador.totalAgentesMenu];
		AdministradorDF.daDeAlta(this, "AgenteMenu", tipo.name());
		addBehaviour(new BuscaAgentesParaMenu(this));
	}
	
	public abstract void escogeRecetas();
	
	public void quitaPropioAID()
	{
		AID[] nuevos = new AID[menus.length - 1];
		AID miAID = getAID();
		
		for (int i = 0, j = 0; i < nuevos.length; i++)
		{
			if (menus[i].equals(miAID))
				continue;
			nuevos[j++] = menus[i];
		}
		
		menus = nuevos;
	}
}
