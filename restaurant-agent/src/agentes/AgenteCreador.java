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
			a.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		doDelete();
	}
}
