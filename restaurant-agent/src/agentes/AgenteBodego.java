package agentes;

import java.io.IOException;
import java.io.PrintWriter;

import comportamientos.BuscaAgentesParaBodego;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
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
	public Behaviour job;
	
	public Tipo tipo;
	public int fuerza;
	public int altura;
	
	public AID chef;
	public AID proveedor;
	
	private int x;
	private int y;
	
	public String cargando = "";
	
	protected void setup(){
		fuerza=450;
		altura=2;
		setup(Tipo.Normal);
	}
	
	protected void setup(Tipo tipo){
		this.tipo = tipo;
		setX(2);
		setY(5 + tipo.ordinal());
		AdministradorDF.daDeAlta(this, "AgenteBodego", "AgenteBodego"+tipo.name());
		addBehaviour(new BuscaAgentesParaBodego(this));
		Debug.print("Bodego "+tipo.name()+" creado.");
	}
	
	public void setX(int x){
		this.x=x;
		actualizarArchivoDeAgente();
	}
	
	public void setY(int y){
		this.y=y;
		actualizarArchivoDeAgente();
	}
	public void actualizarArchivoDeAgente(){
		PrintWriter wr = null;
		try{
			wr = new PrintWriter(tipo.name()+".txt");
			wr.println(x);
			wr.println(y);
			wr.println(cargando);
			wr.close();
		}catch(IOException e){
			Debug.print("Problema al actualizar el archivo del agente "+tipo.name());
		}
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public int obtenerCarrilCercano(){
		if(getX()<7)
			return 3 + tipo.ordinal();
		if(getX()<13)
			return 9 + tipo.ordinal();
		return 15 + tipo.ordinal();
	}
}