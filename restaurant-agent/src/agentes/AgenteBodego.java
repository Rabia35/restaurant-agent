package agentes;

import comportamientos.EscucharMensajes;

import jade.core.AID;
import jade.core.Agent;
import sql.Ingrediente;
import util.AdministradorDF;
import util.Pedido;

public class AgenteBodego extends Agent {

	public enum Tipo {Normal,Fuerte,Alto}
	
	private static final long serialVersionUID = 7814749511523854605L;
	
	public Pedido ingredientePorLlevar = null;
	public Ingrediente ingredientePorAcomodar = null;
	public boolean enContratacion = false;
	
	public Tipo tipo;
	public int fuerza;
	public int altura;
	
	public AID chef;
	public AID proveedor;
	
	public int x;
	public int y;
	
	protected void setup(){
		fuerza=8000;
		altura=2;
		setup(Tipo.Normal);
	}
	
	protected void setup(Tipo tipo){
		this.tipo = tipo;
		AdministradorDF.daDeAlta(this, "AgenteBodego", "AgenteBodego"+tipo.name());
		//chef = AdministradorDF.encuentraAgentes(this, "AgenteChef")[0];
		proveedor = AdministradorDF.encuentraAgentes(this, "AgenteProveedor")[0];
		addBehaviour(new EscucharMensajes(this));
		//addBehaviour(new Almacenar(this));
		//addBehaviour(new Llevar(this));
		System.out.println("Bodego "+tipo.name()+" creado.");
	}
}

