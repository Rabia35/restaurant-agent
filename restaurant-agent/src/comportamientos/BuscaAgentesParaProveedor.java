package comportamientos;

import util.AdministradorDF;
import agentes.AgenteProveedor;
import jade.core.behaviours.WakerBehaviour;

public class BuscaAgentesParaProveedor extends WakerBehaviour{
	
	private static final long serialVersionUID = -3133208435934406627L;
	private static final long tiempoDespertar = 2000;
	
	AgenteProveedor miAgente;
	
	public BuscaAgentesParaProveedor(AgenteProveedor a) 
	{
		super(a, tiempoDespertar);
		miAgente = a;
	}	
	
	@Override
	protected void onWake() {
		super.onWake();
		miAgente.agentesBodegos = AdministradorDF.encuentraAgentes(miAgente, "AgenteBodego");
		System.out.println("Agentes Bodegos agregados para Proveedor"); //DEBUG
		miAgente.addBehaviour(new RevisarPeticiones(miAgente));
	}
}
