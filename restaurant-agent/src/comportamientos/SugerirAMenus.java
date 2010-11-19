package comportamientos;

import util.Mensaje;
import util.Performativas;
import agentes.AgenteMenu;
import jade.core.behaviours.OneShotBehaviour;

public class SugerirAMenus extends OneShotBehaviour {

	private static final long serialVersionUID = -2885592148489765353L;
	
	AgenteMenu miAgente;
	String sugerencia;
	
	public SugerirAMenus(AgenteMenu a, String platillos){
		super(a);
		miAgente=a;
		sugerencia=platillos;
	}
	@Override
	public void action() {
		Mensaje.mandaMensaje(miAgente, Performativas.SUGERIR, miAgente.menus, sugerencia);
		myAgent.addBehaviour(new SugerirAAdministrador(miAgente,sugerencia));
	}

}
