package comportamientos;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;
import sql.Estante;
import sql.Ingrediente;
import util.Pedido;
import util.Performativas;
import agentes.AgenteBodego;


public class ConcretarContratacion extends MsgReceiver{

	private static final long serialVersionUID = -4940852777586806337L;
	
	AgenteBodego miAgente;
	
	Estante estante;
	
	public ConcretarContratacion(AgenteBodego a, long deadline){
		//Agent, MessageTemplate, deadline, DataStore, msgKey
		super(a,MessageTemplate.MatchPerformative(Performativas.CONFIRMAR),deadline,null,null);
		miAgente=a;
	}

	public ConcretarContratacion(AgenteBodego a, long deadline, Estante estante){
		//Agent, MessageTemplate, deadline, DataStore, msgKey
		super(a,MessageTemplate.MatchPerformative(Performativas.CONFIRMAR),deadline,null,null);
		miAgente=a;
		this.estante=estante;
	}
	
	protected void handleMessage(ACLMessage msg) {
		miAgente.enContratacion=false;
		if(msg!=null){
			if(msg.getSender().equals(miAgente.chef)){
				String[]content = msg.getContent().split("#", 2);
				miAgente.ingredientePorLlevar = new Pedido(content[0],Integer.parseInt(content[1]));
				//addBehaviour(new Llevar(this));
			}
			if(msg.getSender().equals(miAgente.proveedor)){
				miAgente.ingredientePorAlmacenar = Ingrediente.obtenerIngrediente(msg.getContent());
				System.out.println(miAgente.getLocalName()+" almacenando "+miAgente.ingredientePorAlmacenar.nombre);
				myAgent.addBehaviour(new Almacenar(miAgente,estante));
			}
		}else if(estante!=null){
			Estante.liberarEstante(estante.posicionX, estante.posicionY, estante.altura);
		}
	}
}
