package agentes;

import java.io.IOException;
import java.io.PrintWriter;

import comportamientos.BuscaAgentesParaMenu;
import comportamientos.SugerirAMenus;

import sql.Menu;
import sql.Receta;
import util.AdministradorDF;
import util.Debug;
import util.TipoMenu;
import jade.core.AID;
import jade.core.Agent;

public abstract class AgenteMenu extends Agent
{
	private static final long serialVersionUID = -8641471475205022401L;
	protected static final int PROPUESTAS_POR_MENU = 3;
	public TipoMenu tipo;
	
	public AID[] menus;
	public AID administrador;	
	protected Menu menu;
	
	protected String[][] recetas;
	
	public void setup() 
	{ 		
		AdministradorDF.daDeAlta(this, "AgenteMenu", tipo.name());
		addBehaviour(new BuscaAgentesParaMenu(this));
	}
	
	public abstract void escogeRecetas();
	
	public void quitaPropioAID()
	{
		AID[] nuevos = new AID[menus.length - 1];
		AID miAID = getAID();
		
		for (int i = 0, j = 0; i < menus.length; i++)
		{
			if (menus[i].equals(miAID))
				continue;
			nuevos[j++] = menus[i];
		}
		
		menus = nuevos;
	}
	
	protected void cargaMenu()
	{
		menu = Menu.obtenerMenu();
	}
	
	protected boolean estaEnMenu(String clave)
	{
		for (int i = 0; i < menu.recetas.length; i++)
			if (menu.recetas[i].clave.equals(clave))
				return true;
		return false;
	}
	
	public void negociaMenu(String propuesta)
	{
		Debug.print(propuesta);
		addBehaviour(new SugerirAMenus(this, propuesta));
	}
	
	public void procesaRecetas()
	{
		String rFinal = "";
		String mensaje = "";
		int t = 0;
		
		cargaMenu();
		
		for (int i = 0; i < recetas.length; i++)
			if(!estaEnMenu(recetas[i][0]))
			{
				rFinal += recetas[i][0] + "#";
				mensaje += Receta.obtenerReceta(recetas[i][0]).nombre + "\n";
				t++;
				if (t == PROPUESTAS_POR_MENU)
					break;
			}
		
		escribeArchivo(mensaje);
		negociaMenu(rFinal);
	}
	
	public String valorReceta(String receta)
	{
		for (int i = 0; i < recetas.length; i++)
			if (recetas[i][0].equals(receta))
				return recetas[i][1];
		return "0";
	}
	
	private void escribeArchivo(String contenido)
	{
		PrintWriter wr = null;
		try
		{
			wr = new PrintWriter(tipo.name() + ".txt");
			wr.println(contenido);
			wr.close();
			
		}catch(IOException e)
		{
			Debug.print("Problema al actualizar el archivo del agente " + tipo.name());
		}	
	}
}
