package comportamientos;

import java.util.ArrayList;

import util.Mensaje;
import util.Performativas;

import agentes.AgenteMenu;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

public class SugerirAAdministrador extends MsgReceiver {

	private static final long serialVersionUID = -4524513586450138792L;
	private static final long tiempoDeEspera = 2000L;
	
	AgenteMenu miAgente;
	int rec;
	ArrayList<String> platillos;
	
	public SugerirAAdministrador(AgenteMenu a, String propuesta){
		this(a,propuesta,0);
	}
	
	public SugerirAAdministrador(AgenteMenu a, String propuesta, int recibidos){
		super(a,MessageTemplate.MatchPerformative(Performativas.SUGERIR),System.currentTimeMillis() + tiempoDeEspera,null,null);
		miAgente=a;
		rec=recibidos;
		this.platillos = new ArrayList<String>();
		String[] platillos = propuesta.split("#");
		for(String platillo: platillos){
			if(platillo!="")
				this.platillos.add(platillo);
		}
	}
	
	protected void handleMessage(ACLMessage msg){
		if(msg!=null){
			String[] platillos = msg.getContent().split("#");
			for(String platillo: platillos){
				if(platillo!="")
					this.platillos.add(platillo);
			}
			rec++;
			if(miAgente.menus.length==rec){
				Mensaje.mandaMensaje(miAgente, Performativas.SUGERIR, miAgente.administrador, propuestaConValores());
				miAgente.addBehaviour(new EsperaCambioDeMenu(miAgente));
			}else{
				miAgente.addBehaviour( new SugerirAAdministrador(miAgente,propuesta(),rec));	
			}
		}else{
			miAgente.addBehaviour( new SugerirAAdministrador(miAgente,propuesta(),rec));
		}
	}
	
	private String propuesta(){
		String propuesta = "";
		for(String platillo: platillos){
			propuesta += platillo + "#";			
		}
		return propuesta;
	}
	
	private String propuestaConValores(){
		String propuesta = "";
		for(String platillo: platillos){
			propuesta += platillo + "#" + miAgente.valorReceta(platillo) +"#";			
		}
		return propuesta;
	}
}
