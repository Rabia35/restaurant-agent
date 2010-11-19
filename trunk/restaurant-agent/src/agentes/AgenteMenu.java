package agentes;

import comportamientos.BuscaAgentesParaMenu;

import sql.Menu;
import util.AdministradorDF;
import util.Debug;
import util.TipoMenu;
import jade.core.AID;
import jade.core.Agent;

public abstract class AgenteMenu extends Agent
{
	private static final long serialVersionUID = -8641471475205022401L;
	protected static final int PROPUESTAS_POR_MENU = 3;
	public TipoMenu tipo;
	
	public AID[] menus;
	public AID administrador;	
	protected Menu menu;

	public void setup() 
	{ 		
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
	
	protected void cargaMenu()
	{
		menu = Menu.obtenerMenu();
	}
	
	protected boolean estaEnMenu(String clave)
	{
		for (int i = 0; i < menu.recetas.length; i++)
			if (menu.recetas[i].clave.equals(clave))
				return true;
		return false;
	}
	
	public void negociaMenu(String propuesta)
	{
		Debug.print(propuesta);
		//Aqui añadir behaviour de negociación
	}
}
