package util;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Mensaje
{
	public static void mandaMensaje(Agent agente, int performativa, AID[] destinatarios, String mensaje)
	{
		ACLMessage msj = new ACLMessage(performativa);
		
		for (int i = 0; i < destinatarios.length; i++)
			msj.addReceiver(destinatarios[i]);
		
		msj.setLanguage("#-Split");
		msj.setOntology("Restaurante");
		msj.setContent(mensaje);
		agente.send(msj);
		System.out.println(msj); //DEBUG
	}
}
