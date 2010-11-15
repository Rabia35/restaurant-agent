package comportamientos;

import sql.Estante;
import sql.Ingrediente;
import util.Debug;
import util.Mensaje;
import util.Performativas;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import agentes.AgenteBodego;

public class EscucharMensajes extends TickerBehaviour {

	private static final long serialVersionUID = -6627936988291059108L;
	private static final long tiempoDeRevision = 500L;
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
				if(msg.getSender().equals(miAgente.chef)){
					responderAChef(msg);
				}
				if(msg.getSender().equals(miAgente.proveedor)){
					responderAProveedor(msg);
				}
			}
		}else{
			clearMessageQueue();
		}
		
	}
	
	private void responderAChef(ACLMessage msg){
		String[]content = msg.getContent().split("#", 2);
		Ingrediente ingredienteSolicitado= Ingrediente.obtenerIngrediente(content[0]);
		int cantidad = Integer.parseInt(content[1]);
		if(ingredienteSolicitado.peso*cantidad<=miAgente.fuerza){
			if(ingredienteSolicitado.hayEnAlmacenSuficientes(cantidad,miAgente.altura)){
				Mensaje.mandaMensaje(msg.getConversationId(), miAgente, Performativas.CONFIRMAR, miAgente.chef, "");
				miAgente.enContratacion=true;
				myAgent.addBehaviour(new ConcretarContratacion(miAgente,System.currentTimeMillis()+2000L));	
			}else{
				Debug.print(miAgente.getLocalName()+" no puede recoger todos los ingredientes");
			}
		}else{
			Debug.print(miAgente.getLocalName()+" no puede cargar los ingredientes");
		}
	}
	
	private void responderAProveedor(ACLMessage msg){
		Debug.print(myAgent.getLocalName()+ " respondiendo a proveedor");
		Ingrediente ingredienteSolicitado= Ingrediente.obtenerIngrediente(msg.getContent());
		Estante estante;
		if(ingredienteSolicitado.refrigerado){
			estante = Estante.apartarRefriLibre(miAgente.altura);
		}else{
			estante = Estante.apartarEstanteLibre(miAgente.altura);	
		}
		if(estante!=null){
			if(ingredienteSolicitado.peso*ingredienteSolicitado.cantidadPorPaquete<=miAgente.fuerza){
				Mensaje.mandaMensaje(msg.getConversationId(),miAgente, Performativas.CONFIRMAR, miAgente.proveedor, "");
				miAgente.enContratacion=true;
				myAgent.addBehaviour(new ConcretarContratacion(miAgente,System.currentTimeMillis()+2000L,estante));
			}else{
				Debug.print(miAgente.getLocalName()+" no puede cargar el paquete");//DEBUG
				Estante.liberarEstante(estante.posicionX, estante.posicionY, estante.altura);
			}
		}else{
			Debug.print(myAgent.getLocalName()+"no encontró un estante disponible");
		}
	}
	
	private void clearMessageQueue(){
		while (myAgent.receive() != null) {
			  ;
		}	
	}	
}