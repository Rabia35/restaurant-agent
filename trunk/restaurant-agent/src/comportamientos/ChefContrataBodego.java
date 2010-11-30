package comportamientos;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;
import sql.Ingrediente;
import sql.Menu;
import util.Debug;
import util.Mensaje;
import util.Performativas;
import agentes.AgenteChef;

public class ChefContrataBodego extends MsgReceiver
{
	private static final long serialVersionUID = -1791386902205059086L;
	private static final long TIEMPO_ESPERA = 10000L;
	private static final int INTENTOS = 200;
	
	private int intento;
	private Ingrediente ing;
	private int cuantos;
	private String id;

	public AgenteChef miAgente;	
	
	public ChefContrataBodego(AgenteChef a, String id, Ingrediente ing, int cuantos, int intento)
	{
		//Agent, MessageTemplate, deadline, DataStore, msgKey
		super(a,MessageTemplate.MatchConversationId(id),System.currentTimeMillis() + TIEMPO_ESPERA,null,null);
				
		miAgente=a;
		this.id=id;
		this.intento = intento;
		this.cuantos = cuantos;
		this.ing = ing;
	}

	protected void handleMessage(ACLMessage msg) 
	{
		if(msg==null)
		{
			if (intento <= INTENTOS)
			{
				miAgente.negociaIngrediente(ing, cuantos, id, intento);
			}else
			{
				miAgente.menu = Menu.obtenerMenu();
				Debug.print("No se recibió ingrediente: " + ing.clave + " X " + cuantos);
			}			
		}else
		{
			Mensaje.mandaMensaje(id, miAgente, Performativas.CONFIRMAR, msg.getSender(), ing.clave + "#" + cuantos); 
		}
	}
}
