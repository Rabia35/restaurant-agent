package util;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class Mensaje
{
	public static void mandaMensaje(Agent agente, int performativa, AID[] destinatarios, String mensaje)
	{
		mandaMensaje("",agente,performativa,destinatarios,mensaje);
	}
	
	public static void mandaMensaje(Agent agente, int performativa, AID destinatario, String mensaje)
	{
		mandaMensaje("",agente,performativa,new AID[]{destinatario},mensaje);
	}
	
	public static void mandaMensaje(String id, Agent agente, int performativa, AID destinatario, String mensaje){
		mandaMensaje(id,agente,performativa,new AID[]{destinatario},mensaje);
	}
	
	public static void mandaMensaje(String id, Agent agente, int performativa, AID[] destinatarios, String mensaje)
	{
		ACLMessage msj = new ACLMessage(performativa);
		
		for (int i = 0; i < destinatarios.length; i++)
			msj.addReceiver(destinatarios[i]);
		
		msj.setConversationId(id);
		msj.setLanguage("#-Split");
		msj.setOntology("Restaurante");
		msj.setContent(mensaje);
		agente.send(msj);
		printMessage(msj); //DEBUG
	}
	
	private static void printMessage(ACLMessage msj){
		System.out.println("---------");
		System.out.println("Mensaje enviado:");
		System.out.println("  "+Performativas.getNombre(msj.getPerformative()));
		System.out.println("  :id de conversación - "+msj.getConversationId());
		System.out.println("  :emisor - "+msj.getSender().getLocalName());
		System.out.print("  :receptor - ");
		Iterator receptores =msj.getAllReceiver();
		while(receptores.hasNext())
			System.out.print(((AID)receptores.next()).getLocalName()+" ");
		System.out.println("\n  :contenido "+msj.getContent());
		System.out.println("---------");
	}
}
