package comportamientos;

import sql.Ingrediente;
import util.Mensaje;
import util.Performativas;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import agentes.AgenteBodego;

public class EscucharMensajes extends TickerBehaviour {

	private static final long serialVersionUID = -6627936988291059108L;
	private static final long tiempoDeRevision = 1000L;
	AgenteBodego miAgente;
	
	public EscucharMensajes(AgenteBodego a) {
		super(a,tiempoDeRevision);
		miAgente = a;
	}

	
	@Override
	protected void onTick() {
		if(!miAgente.enContratacion && miAgente.ingredientePorAcomodar==null && miAgente.ingredientePorLlevar==null){
			ACLMessage msg = myAgent.receive();
			if(msg!=null){
				if(msg.getSender()==miAgente.chef){
					responderAChef(msg);
				}
				if(msg.getSender()==miAgente.proveedor){
					responderAProveedor(msg);
				}
			}
		}
	}
	
	private void responderAChef(ACLMessage msg){
		String[]content = msg.getContent().split("#", 2);
		Ingrediente ingredienteSolicitado= Ingrediente.obtenerIngrediente(content[0]);
		responder(miAgente.chef, ingredienteSolicitado, Integer.parseInt(content[1]) );
	}
	
	private void responderAProveedor(ACLMessage msg){
		Ingrediente ingredienteSolicitado= Ingrediente.obtenerIngrediente(msg.getContent());
		responder(miAgente.proveedor, ingredienteSolicitado, ingredienteSolicitado.cantidadPorPaquete);
	}
	
	private void responder(AID destinatario, Ingrediente ingrediente, int cantidad){
		if(ingrediente.peso*cantidad<=miAgente.fuerza){
			Mensaje.mandaMensaje(miAgente, Performativas.CONFIRMAR, destinatario, "");
			miAgente.enContratacion=true;
			myAgent.addBehaviour(new ConcretarContratacion(miAgente,System.currentTimeMillis()+2000L));
		}
	}
}