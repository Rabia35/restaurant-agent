package comportamientos;

import jade.core.behaviours.WakerBehaviour;
import util.AdministradorDF;
import util.Debug;
import agentes.AgenteChef;

public class BuscaAgentesParaChef extends WakerBehaviour
{	
	private static final long serialVersionUID = -4372536552672850791L;
	private static final long tiempoDespertar = 3000;
	
	AgenteChef miAgente;
	
	public BuscaAgentesParaChef(AgenteChef a) 
	{
		super(a, tiempoDespertar);
		miAgente = a;
	}	
	
	@Override
	protected void onWake() {
		super.onWake();
		miAgente.addBehaviour(new PidePlatillo(miAgente));
		miAgente.agentesBodegos = AdministradorDF.encuentraAgentes(miAgente, "AgenteBodego");
		Debug.print("Agentes Bodegos agregados para Chef"); //DEBUG
	}
}
