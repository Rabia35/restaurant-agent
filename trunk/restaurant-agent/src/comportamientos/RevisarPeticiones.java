package comportamientos;

import agentes.AgenteProveedor;
import sql.Ingrediente;
import sql.Peticion;
import util.Mensaje;
import util.Performativas;
import jade.core.behaviours.TickerBehaviour;


public class RevisarPeticiones extends TickerBehaviour {

	private static final long serialVersionUID = -8423084959540874770L;
	private static final long tiempoDeRevision = 1000;
	
	AgenteProveedor miAgente;
	
	public RevisarPeticiones(AgenteProveedor a) {
		super(a,tiempoDeRevision);
		miAgente = a;
	}
	
	@Override
	protected void onTick() {
		if(miAgente.ingredientePorAcomodar==null){
			Ingrediente ingredientePedido = Peticion.obtenerPeticion();
			if(ingredientePedido!=null){
				miAgente.ingredientePorAcomodar = ingredientePedido;
				Mensaje.mandaMensaje(miAgente, Performativas.INFORMAR, miAgente.agentesBodegos, ingredientePedido.clave);
				myAgent.addBehaviour(new ContratarBodego(miAgente,System.currentTimeMillis()+2000L));
			}
		}
	}

}
