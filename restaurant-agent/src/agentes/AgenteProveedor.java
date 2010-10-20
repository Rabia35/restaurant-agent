package agentes;

import comportamientos.BuscaAgentesParaProveedor;
import comportamientos.RevisarPeticiones;

import sql.Ingrediente;
import util.AdministradorDF;
import jade.core.AID;
import jade.core.Agent;

public class AgenteProveedor extends Agent {

	private static final long serialVersionUID = -6300185715958185382L;
	public Ingrediente ingredientePorAcomodar = null;
	public AID[] agentesBodegos;
	
	
	protected void setup(){
		addBehaviour(new BuscaAgentesParaProveedor(this));
		addBehaviour(new RevisarPeticiones(this));
		AdministradorDF.daDeAlta(this, "AgenteProveedor", "AgenteProveedor");	
		agentesBodegos= null;
		System.out.println("Proveedor creado.");
	}
}
