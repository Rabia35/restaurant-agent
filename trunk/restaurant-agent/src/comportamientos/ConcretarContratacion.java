package comportamientos;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;
import sql.Ingrediente;
import util.Pedido;
import util.Performativas;
import agentes.AgenteBodego;


public class ConcretarContratacion extends MsgReceiver{

	private static final long serialVersionUID = -4940852777586806337L;
	
	AgenteBodego miAgente;
	
	public ConcretarContratacion(AgenteBodego a, long deadline){
		//Agent, MessageTemplate, deadline, DataStore, msgKey
		super(a,MessageTemplate.MatchPerformative(Performativas.CONFIRMAR),deadline,null,null);
		miAgente=a;
	}

	protected void handleMessage(ACLMessage msg) {
		miAgente.enContratacion=false;
		if(msg!=null){
			if(msg.getSender()==miAgente.chef){
				String[]content = msg.getContent().split("#", 2);
				miAgente.ingredientePorLlevar = new Pedido(content[0],Integer.parseInt(content[1]));
			}
			if(msg.getSender()==miAgente.proveedor){
				miAgente.ingredientePorAcomodar = Ingrediente.obtenerIngrediente(msg.getContent());
			} 
		}
	}
}
