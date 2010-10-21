package comportamientos;

import sql.Estante;
import sql.Ingrediente;
import util.Mensaje;
import util.Performativas;
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
		if(!miAgente.enContratacion && miAgente.ingredientePorAlmacenar==null && miAgente.ingredientePorLlevar==null){
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
		if(ingredienteSolicitado.peso*Integer.parseInt(content[1])<=miAgente.fuerza){
			Mensaje.mandaMensaje(miAgente, Performativas.CONFIRMAR, miAgente.chef, "");
			miAgente.enContratacion=true;
			myAgent.addBehaviour(new ConcretarContratacion(miAgente,System.currentTimeMillis()+2000L));
		}
	}
	
	private void responderAProveedor(ACLMessage msg){
		Estante estante = Estante.apartarEstanteLibre(miAgente.altura);
		if(estante!=null){
			Ingrediente ingredienteSolicitado= Ingrediente.obtenerIngrediente(msg.getContent());
			if(ingredienteSolicitado.peso*ingredienteSolicitado.cantidadPorPaquete<=miAgente.fuerza){
				Mensaje.mandaMensaje(miAgente, Performativas.CONFIRMAR, miAgente.proveedor, "");
				miAgente.enContratacion=true;
				myAgent.addBehaviour(new ConcretarContratacion(miAgente,System.currentTimeMillis()+2000L,estante));
			}else{
				Estante.liberarEstante(estante.posicionX, estante.posicionY, estante.altura);
			}
		}
	}
	
}