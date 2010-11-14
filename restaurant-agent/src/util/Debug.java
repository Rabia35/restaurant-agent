package util;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class Debug 
{
	public static boolean depurando = true;
	
	public static void print(String s)
	{
		if (depurando)
			System.out.println(s);
	}
	
	public static void printMessage(ACLMessage msj){
		if (!depurando)
			return;
		synchronized(Debug.class){
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
}
