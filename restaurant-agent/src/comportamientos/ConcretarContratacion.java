package comportamientos;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;
import sql.Estante;
import sql.Ingrediente;
import util.Debug;
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
			if(miAgente.job!=null)
				myAgent.removeBehaviour(miAgente.job);
			if(msg.getSender().equals(miAgente.chef)){
				String[]content = msg.getContent().split("#", 2);
				miAgente.ingredientePorLlevar = new Pedido(content[0],Integer.parseInt(content[1]));
				Debug.print(miAgente.getLocalName()+" recogiendo "+miAgente.ingredientePorLlevar.getIngrediente().nombre);
				miAgente.job = new Llevar(miAgente);
				myAgent.addBehaviour(miAgente.job);
			}
			if(msg.getSender().equals(miAgente.proveedor)){
				miAgente.ingredientePorAlmacenar = Ingrediente.obtenerIngrediente(msg.getContent());
				Debug.print(miAgente.getLocalName()+" almacenando "+miAgente.ingredientePorAlmacenar.nombre);
				miAgente.job = new Almacenar(miAgente,estante);
				myAgent.addBehaviour(miAgente.job);
			}
		}else if(estante!=null){
			Estante.liberarEstante(estante.posicionX, estante.posicionY, estante.altura);
		}
	}
}
