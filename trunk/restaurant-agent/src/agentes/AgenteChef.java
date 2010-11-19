package agentes;

import sql.Ingrediente;
import sql.Menu;
import util.AdministradorDF;
import util.Debug;
import util.Mensaje;
import util.Performativas;

import comportamientos.BuscaAgentesParaChef;
import comportamientos.ChefContrataBodego;

import jade.core.AID;
import jade.core.Agent;

public class AgenteChef extends Agent{

	private static final long serialVersionUID = 3109891387777615419L;
	public AID[] agentesBodegos;
	
	public Menu menu;
	
	public int idConversacion = 0;
	
	protected void setup(){		
		menu = Menu.obtenerMenu();		
		//addBehaviour(new BuscaAgentesParaChef(this));		
		AdministradorDF.daDeAlta(this, "AgenteChef", "AgenteChef");	
		Debug.print("Chef creado.");
	}

	public void negociaIngrediente(Ingrediente ing, int cuantos)
	{
		negociaIngrediente(ing, cuantos, "Ingrediente: " + idConversacion, 0);
		idConversacion++;
	}
	
	public void negociaIngrediente(Ingrediente ing, int cuantos, String id, int intentos)
	{
		Mensaje.mandaMensaje(id, this, Performativas.TRAER, 
				agentesBodegos, ing.clave + "#" + cuantos);
		addBehaviour(new ChefContrataBodego(this, id, ing, cuantos, intentos + 1));		
	}
	
}
