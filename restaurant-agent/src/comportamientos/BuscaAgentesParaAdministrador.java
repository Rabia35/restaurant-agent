package comportamientos;

import util.AdministradorDF;
import util.Debug;
import agentes.AgenteAdministrador;
import jade.core.behaviours.WakerBehaviour;

public class BuscaAgentesParaAdministrador extends WakerBehaviour
{
	private static final long serialVersionUID = -7346855023302739824L;
	private static final long tiempoDespertar = 2000;
	
	AgenteAdministrador miAgente;
	
	public BuscaAgentesParaAdministrador(AgenteAdministrador a) 
	{
		super(a, tiempoDespertar);
		miAgente = a;
	}	
	
	@Override
	protected void onWake() {
		super.onWake();
		miAgente.agentesMenu = AdministradorDF.encuentraAgentes(miAgente, "AgenteMenu");
		miAgente.addBehaviour(new CambioDeMenu(miAgente));		
		Debug.print("Agentes Menu agregados para Administrador"); //DEBUG
	}
}
