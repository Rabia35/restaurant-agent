package comportamientos;

import sql.Receta;
import util.Performativas;
import agentes.AgenteAdministrador;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

public class RecibirPropuestasMenu extends MsgReceiver 
{
	private static final long serialVersionUID = -5556717967302499627L;
	private static final long tiempoDeEspera = 2000L;
	
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
		System.out.println(msg);
		if(msg != null)
			descomponMensaje(msg.getContent());
		
		if (identificador < miAgente.totalAgentesMenu)
			esperaSiguiente();
		else
			evaluaPropuesta();
	}	
	
	private void evaluaPropuesta() 
	{		
		int max = 0;
		int indice = 0;
		int total = 0;
		for (int k = 0; k < (miAgente.totalAgentesMenu * miAgente.totalAgentesMenu); k++)
		{
			total = 0;
			for (int i = 0; i < miAgente.totalAgentesMenu; i++)		
				total += miAgente.valores[i][k];
			if (total > max)
			{
				max = total;
				indice = k;
			}			
		}
		String s = miAgente.propuestas[0][indice];
		miAgente.menu.agregaReceta(Receta.obtenerReceta(s));
		System.out.println("Nueva receta en el menu: " + s + " con un beneficio de " + total); //DEBUG
	}

	private void descomponMensaje(String contenido) 
	{
		String splitted[] = contenido.split("#");
		for (int i = 0; i < splitted.length; i += 2)
		{
			miAgente.propuestas[identificador - 1][i / 2] = splitted[i];
			miAgente.valores[identificador - 1][i / 2] = Integer.parseInt(splitted[i + 1]);			
		}
		System.out.println("Propuesta #" + identificador + " recibida."); //DEBUG
	}

	private void esperaSiguiente()
	{	
		miAgente.addBehaviour(new RecibirPropuestasMenu(miAgente, identificador + 1));
	}
}
