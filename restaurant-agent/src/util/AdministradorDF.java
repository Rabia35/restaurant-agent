package util;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AdministradorDF 
{
	public static void daDeAlta(Agent agente, String tipo, String nombre)
	{
		DFAgentDescription df = new DFAgentDescription();
		df.setName(agente.getAID());
		
		ServiceDescription sd = new ServiceDescription();
		sd.setType(tipo);
		sd.setName(nombre);
		
		df.addServices(sd);
		
		try {
			DFService.register(agente, df);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	public static AID[] encuentraAgentes(Agent agente, String tipo)
	{
		AID ret[] = null;
		
		DFAgentDescription df = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setType(tipo);
		df.addServices(sd);
		
		try {
			DFAgentDescription[] res = DFService.search(agente, df);
			ret = new AID[res.length];
			
			for (int i = 0; i < res.length; i++)
				ret[i] = res[i].getName();
		} catch (FIPAException e) {
			e.printStackTrace();
		} 
		
		return ret;
	}
	
}
