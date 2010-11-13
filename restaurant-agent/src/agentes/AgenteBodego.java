package agentes;

import java.io.IOException;
import java.io.PrintWriter;

import comportamientos.EscucharMensajes;

import jade.core.AID;
import jade.core.Agent;
import sql.Ingrediente;
import util.AdministradorDF;
import util.Debug;
import util.Pedido;

public class AgenteBodego extends Agent {

	public enum Tipo {Normal,Fuerte,Alto}
	
	private static final long serialVersionUID = 7814749511523854605L;
	
	public Pedido ingredientePorLlevar = null;
	public Ingrediente ingredientePorAlmacenar = null;
	public boolean enContratacion = false;
	
	public Tipo tipo;
	public int fuerza;
	public int altura;
	
	public AID chef;
	public AID proveedor;
	
	private int x;
	private int y;
	
	protected void setup(){
		fuerza=350;
		altura=2;
		setup(Tipo.Normal);
	}
	
	protected void setup(Tipo tipo){
		this.tipo = tipo;
		setX(2);
		setY(5 + tipo.ordinal());
		AdministradorDF.daDeAlta(this, "AgenteBodego", "AgenteBodego"+tipo.name());
		//chef = AdministradorDF.encuentraAgentes(this, "AgenteChef")[0];
		proveedor = AdministradorDF.encuentraAgentes(this, "AgenteProveedor")[0];
		addBehaviour(new EscucharMensajes(this));
		Debug.print("Bodego "+tipo.name()+" creado.");
	}
	
	public void setX(int x){
		this.x=x;
		try{
			PrintWriter wr = new PrintWriter(tipo.name()+".txt");
			wr.print(x + " " +y);
			wr.close();
		}catch(IOException e){
			Debug.print("Problema al actualizar la posición en el archivo del agente "+tipo.name());
		}
	}
	
	public void setY(int y){
		this.y=y;
		try{
			PrintWriter wr = new PrintWriter(tipo.name()+".txt");
			wr.print(x + " " +y);
			wr.close();
		}catch(IOException e){
			Debug.print("Problema al actualizar la posición en el archivo del agente "+tipo.name());
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}