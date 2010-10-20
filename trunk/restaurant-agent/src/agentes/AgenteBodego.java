package agentes;

import jade.core.AID;
import jade.core.Agent;
import sql.Ingrediente;
import util.AdministradorDF;

public class AgenteBodego extends Agent {

	public enum Tipo {Normal,Fuerte,Alto}
	private static final long serialVersionUID = 7814749511523854605L;
	
	public Ingrediente ingredientePorLlevar = null;
	public Ingrediente ingredientePorAcomodar = null;
	public boolean enContratacion = false;
	
	public Tipo tipo;
	public int fuerza;
	public int altura;
	
	public AID chef;
	public AID proveedor;
	
	protected void setup(){
		fuerza=2;
		altura=2;
		setup(Tipo.Normal);
	}
	
	protected void setup(Tipo tipo){
		this.tipo = tipo;
		//addBehaviour(); Escuchar mensajes chef
		//addBehaviour(); Escuchar mensajes proveedor
		AdministradorDF.daDeAlta(this, "AgenteBodego", "AgenteBodego"+tipo.name());
		//chef = AdministradorDF.encuentraAgentes(this, "AgenteChef")[0];
		proveedor = AdministradorDF.encuentraAgentes(this, "AgenteProveedor")[0];
		System.out.println("Bodego "+tipo.name()+" creado.");
	}
}
