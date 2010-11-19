package comportamientos;

import jade.core.behaviours.WakerBehaviour;
import util.AdministradorDF;
import util.Debug;
import agentes.AgenteBodego;

public class BuscaAgentesParaBodego extends WakerBehaviour{
	
	private static final long serialVersionUID = -6302492289973932593L;

	private static final long tiempoDespertar = 3000;
	
	AgenteBodego miAgente;
	
	public BuscaAgentesParaBodego(AgenteBodego a) 
	{
		super(a, tiempoDespertar);
		miAgente = a;
	}	
	
	@Override
	protected void onWake() {
		super.onWake();
		miAgente.chef = AdministradorDF.encuentraAgentes(miAgente, "AgenteChef")[0];
		miAgente.proveedor = AdministradorDF.encuentraAgentes(miAgente, "AgenteProveedor")[0];
		Debug.print("Agentes Chef y Proveedor agregados para Bodego"); //DEBUG
		myAgent.addBehaviour(new EscucharMensajes(miAgente));;
	}
}
