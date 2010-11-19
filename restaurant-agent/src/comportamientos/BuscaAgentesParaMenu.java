package comportamientos;

import util.AdministradorDF;
import util.Debug;
import agentes.AgenteMenu;
import jade.core.behaviours.WakerBehaviour;

public class BuscaAgentesParaMenu extends WakerBehaviour
{
	private static final long serialVersionUID = 6027000758430690937L;
	private static final long tiempoDespertar = 3000;
	
	public AgenteMenu miAgente; 
	
	public BuscaAgentesParaMenu(AgenteMenu a) 
	{
		super(a, tiempoDespertar);
		miAgente = a;
	}	
	
	@Override
	protected void onWake() {
		super.onWake();
		miAgente.addBehaviour(new EsperaCambioDeMenu(miAgente));
		miAgente.menus = AdministradorDF.encuentraAgentes(miAgente, "AgenteMenu");
		miAgente.administrador = AdministradorDF.encuentraAgentes(miAgente, "AgenteAdministrador")[0];
		miAgente.quitaPropioAID();
		Debug.print("Agentes agregados para " + miAgente.tipo.name()); //DEBUG
	}
}
