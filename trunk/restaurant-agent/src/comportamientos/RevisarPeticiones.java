package comportamientos;

import agentes.AgenteProveedor;
import sql.Ingrediente;
import sql.Peticion;
import util.Mensaje;
import util.Performativas;
import jade.core.behaviours.CyclicBehaviour;


public class RevisarPeticiones extends CyclicBehaviour {

	AgenteProveedor miAgente;
	
	public RevisarPeticiones(AgenteProveedor a) {
		super(a);
		miAgente = a;
	}

	private static final long serialVersionUID = -8423084959540874770L;
	@Override
	public void action() {
		if(miAgente.ingredientePorAcomodar==null){
			Ingrediente ingredientePedido = Peticion.obtenerPeticion();
			if(ingredientePedido!=null){
				miAgente.ingredientePorAcomodar = ingredientePedido;
				Mensaje.mandaMensaje(miAgente, Performativas.INFORMAR, miAgente.agentesBodegos, ingredientePedido.clave);
				myAgent.addBehaviour(new ContratarBodego(miAgente,2000L));
			}
		}
	}

}
