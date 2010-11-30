package comportamientos;

import sql.Receta;
import util.Debug;
import util.Performativas;
import agentes.AgenteAdministrador;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

public class RecibirPropuestasMenu extends MsgReceiver 
{
	private static final long serialVersionUID = -5556717967302499627L;
	private static final long tiempoDeEspera = 2000L;
	
	private static final int LIMITE_PRUDENTE = 50;
	private static final int LIMITE_SELECCION_PRUDENTE = 70;
	
	private int identificador;
	private AgenteAdministrador miAgente;
	
	public RecibirPropuestasMenu(AgenteAdministrador a, int identificador) 
	{		
		super(a, MessageTemplate.MatchPerformative(Performativas.SUGERIR), System.currentTimeMillis() + tiempoDeEspera, null, null);
		miAgente = a;
		this.identificador = identificador;	
		block();
	}

	@Override
	protected void handleMessage(ACLMessage msg) 
	{
		if(msg == null)
		{
			miAgente.addBehaviour(new RecibirPropuestasMenu(miAgente, identificador));
			return;
		}
			
		descomponMensaje(msg.getContent(), msg.getSender().getLocalName().equals("AgenteMenuPrudente"));
		
		if (identificador < AgenteAdministrador.totalAgentesMenu)
			esperaSiguiente();
		else
			evaluaPropuesta();
	}	
	
	private void evaluaPropuesta() 
	{		
		String mejor = "";
		int maximo = 0;
		
		for (String llave : miAgente.map.keySet())
		{
			int t = miAgente.map.get(llave);
			if (t > maximo)
			{
				maximo = t;
				mejor = llave;
			}			
		}
		
		miAgente.menu.agregaReceta(Receta.obtenerReceta(mejor));
		miAgente.map.clear();
		Debug.print("Nueva receta en el menu: " + mejor + " con un beneficio de " + maximo); //DEBUG
		
	}

	private void descomponMensaje(String contenido, boolean esPrudente) 
	{
		String splitted[] = contenido.split("#");
		for (int i = 0; i < splitted.length; i += 2)
		{
			int valor = (int) Math.floor(Double.parseDouble(splitted[i + 1]));
			
			if (esPrudente && valor >= LIMITE_PRUDENTE)
				valor += valor;
			if (esPrudente && valor >= LIMITE_SELECCION_PRUDENTE)
				valor *= valor;
			
			miAgente.añadeReceta( splitted[i], valor);			
		}
		Debug.print("Propuesta #" + identificador + " recibida."); //DEBUG
	}

	private void esperaSiguiente()
	{	
		miAgente.addBehaviour(new RecibirPropuestasMenu(miAgente, identificador + 1));
	}
}
