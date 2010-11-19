package agentes;

import java.io.IOException;
import java.io.PrintWriter;

import comportamientos.BuscaAgentesParaProveedor;

import sql.Ingrediente;
import util.AdministradorDF;
import util.Debug;
import jade.core.AID;
import jade.core.Agent;

public class AgenteProveedor extends Agent {

	private static final long serialVersionUID = -6300185715958185382L;
	public Ingrediente ingredientePorAcomodar = null;
	public AID[] agentesBodegos;
	public int idConversacion=0;
	
	public String columnaUno = "0";
	public String columnaDos = "0";
	public String columnaTres = "0";
	
	protected void setup(){
		addBehaviour(new BuscaAgentesParaProveedor(this));
		AdministradorDF.daDeAlta(this, "AgenteProveedor", "AgenteProveedor");	
		agentesBodegos= null;
		Debug.print("Proveedor creado.");
	}
	
	public void actualizarColumnas(String uno, String dos, String tres){
		columnaUno = uno;
		columnaDos = dos;
		columnaTres = tres;
		try{
			PrintWriter wr = new PrintWriter("proveedor.txt");
			wr.println(uno);
			wr.println(dos);
			wr.println(tres);
			wr.close();
		}catch(IOException e){
			Debug.print("Problema al actualizar el archivo del agente proveedor");
		}
	}
}
