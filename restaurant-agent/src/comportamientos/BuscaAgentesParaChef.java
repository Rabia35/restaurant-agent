package comportamientos;

import jade.core.behaviours.WakerBehaviour;
import util.AdministradorDF;
import agentes.AgenteChef;

public class BuscaAgentesParaChef  extends WakerBehaviour
{
	
	private static final long serialVersionUID = -4372536552672850791L;
	private static final long tiempoDespertar = 2000;
	
	AgenteChef miAgente;
	
	public BuscaAgentesParaChef(AgenteChef a) 
	{
		super(a, tiempoDespertar);
		miAgente = a;
	}	
	
	@Override
	protected void onWake() {
		super.onWake();
		miAgente.agentesBodegos = AdministradorDF.encuentraAgentes(miAgente, "AgenteBodego");
		System.out.println("Agentes Bodegos agregados para Chef"); //DEBUG
	}
}
