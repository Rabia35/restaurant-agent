package comportamientos;

import util.Performativas;
import agentes.AgenteMenu;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

public class EsperaCambioDeMenu extends MsgReceiver
{
	private static final long serialVersionUID = -4804835145221823315L;
	private static final long tiempoEspera = 10000L;
	AgenteMenu menu;

	public EsperaCambioDeMenu(AgenteMenu a)
	{
		//Agent, MessageTemplate, deadline, DataStore, msgKey
		super(a,MessageTemplate.MatchPerformative(Performativas.CAMBIAR),System.currentTimeMillis() + tiempoEspera,null,null);
		menu = a;
	}

	protected void handleMessage(ACLMessage msg) 
	{
		if(msg==null)
		{
			menu.addBehaviour(new EsperaCambioDeMenu(menu));
		}else
		{
			menu.escogeRecetas();	
		}
	}
}
