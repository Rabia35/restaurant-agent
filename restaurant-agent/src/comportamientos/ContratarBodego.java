package comportamientos;

import util.Mensaje;
import util.Performativas;
import agentes.AgenteProveedor;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

public class ContratarBodego extends MsgReceiver{

	private static final long serialVersionUID = -4902283519265681261L;
	AgenteProveedor miAgente;
	
	String id;
	public ContratarBodego(AgenteProveedor a, String id, long deadline){
		//Agent, MessageTemplate, deadline, DataStore, msgKey
		super(a,MessageTemplate.MatchConversationId(id),System.currentTimeMillis() + deadline,null,null);
		miAgente=a;
		this.id=id;
	}

	protected void handleMessage(ACLMessage msg) {
		if(msg==null){
			Mensaje.mandaMensaje(id,miAgente, Performativas.INFORMAR, miAgente.agentesBodegos, miAgente.ingredientePorAcomodar.clave);
			myAgent.addBehaviour(new ContratarBodego(miAgente,id,2000L));
		}else{
			Mensaje.mandaMensaje(id,miAgente, Performativas.CONFIRMAR, msg.getSender(), miAgente.ingredientePorAcomodar.clave); 
			actualizarColumnas(msg.getSender());
			miAgente.ingredientePorAcomodar=null;
		}
	}
	
	private void actualizarColumnas(AID contratado){
		if(contratado.getLocalName().equals("AgenteBodegoNormal"))
			miAgente.actualizarColumnas(miAgente.ingredientePorAcomodar.clave, miAgente.columnaDos, miAgente.columnaTres);
		if(contratado.getLocalName().equals("AgenteBodegoFuerte"))
			miAgente.actualizarColumnas(miAgente.columnaUno, miAgente.ingredientePorAcomodar.clave, miAgente.columnaTres);
		if(contratado.getLocalName().equals("AgenteBodegoAlto"))
			miAgente.actualizarColumnas(miAgente.columnaUno, miAgente.columnaDos, miAgente.ingredientePorAcomodar.clave);
	}
	
}
