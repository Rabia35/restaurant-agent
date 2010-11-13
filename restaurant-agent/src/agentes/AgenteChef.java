package agentes;

import util.AdministradorDF;
import util.Debug;

import comportamientos.BuscaAgentesParaChef;

import jade.core.AID;
import jade.core.Agent;

public class AgenteChef extends Agent{

	private static final long serialVersionUID = 3109891387777615419L;
	public AID[] agentesBodegos;
	
	protected void setup(){
		addBehaviour(new BuscaAgentesParaChef(this));
		AdministradorDF.daDeAlta(this, "AgenteChef", "AgenteChef");	
		Debug.print("Chef creado.");
	}

}
