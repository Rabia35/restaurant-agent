package agentes;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class AgenteCreador extends Agent 
{
	private static final long serialVersionUID = -6507223481590363240L;

	@Override
	protected void setup() 
	{	
		ContainerController c = getContainerController();
		try {
			AgentController a = c.createNewAgent("AgenteAdministrador", "agentes.AgenteAdministrador", null);
			AgentController p = c.createNewAgent("AgenteProveedor", "agentes.AgenteProveedor", null);
			AgentController b1 = c.createNewAgent("AgenteBodegoNormal", "agentes.AgenteBodego",null);
			AgentController b2 = c.createNewAgent("AgenteBodegoFuerte", "agentes.AgenteBodegoFuerte",null);
			AgentController b3 = c.createNewAgent("AgenteBodegoAlto", "agentes.AgenteBodegoAlto",null);
			a.start();
			p.start();
			b1.start();
			b2.start();
			b3.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}		
		
		doDelete();
	}
}
