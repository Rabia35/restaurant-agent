package sql;

import java.sql.ResultSet;
import java.sql.Timestamp;

import agentes.AgenteBodego;

import util.*;

public class Estante 
{
	public int posicionX;
	public int posicionY;
	public int altura;
	public boolean refrigerador;		
	
	public Ingrediente ingrediente;	
	public Timestamp fechaColocado;
	public int cantidad;
		
	public static Estante obtenerEstante(int x, int y, int altura)
	{
		Estante est = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select * from estante where posicionX = " + x +
					                        " and posicionY = " + y +
					                        " and altura = " + altura);
			rs.next();
			
			est = new Estante();					
			
			est.posicionX = x;
			est.posicionY = y;
			est.altura = altura;
			est.refrigerador = rs.getBoolean("refrigerador");
			est.ingrediente = Ingrediente.obtenerIngrediente(rs.getString("ingrediente"));
			est.fechaColocado = rs.getTimestamp("fechaColocado");
			est.cantidad = rs.getInt("cantidad");
			
			
			bd.desconectar();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return est;
	}
	
	public static Estante obtenerEstante(Ingrediente ingrediente, int altura){
		Estante est = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select * from estante where altura <= " + altura+
											" and ingrediente="+ NoSQLInjection.comillas(ingrediente.clave)+
											" order by altura desc");
			
			if(rs.first()){
				est = new Estante();									
				est.posicionX = rs.getInt("posicionX");
				est.posicionY = rs.getInt("posicionY");
				est.altura = rs.getInt("altura");
				est.refrigerador = rs.getBoolean("refrigerador");
				est.ingrediente = ingrediente;
				est.fechaColocado = rs.getTimestamp("fechaColocado");
				est.cantidad = rs.getInt("cantidad");
			}else{
				return null;
			}
			bd.desconectar();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return est;
	}
	
	public void retirarIngrediente(int cantidad)
	{
		if(ingrediente == null)
			return;
		
		if(cantidad > this.cantidad)
			cantidad = this.cantidad;
		
		this.cantidad -= cantidad;
		
		BaseDeDatos bd = new BaseDeDatos();				
		
		if (this.cantidad == 0)
		{
			bd.ejectuarDML(" update estante set ingrediente = null, cantidad = 0, fechaColocado = null" +
					         " where posicionX = " + posicionX + 
					         " and posicionY = " +  posicionY + 
					         " and altura = " + altura);
			ingrediente = null;
			fechaColocado = null;
		}
		else
			bd.ejectuarDML(" update estante set cantidad = " + this.cantidad + 
			         " where posicionX = " + posicionX + 
			         " and posicionY = " +  posicionY + 
			         " and altura = " + altura);
		
	}
	
	
	public void ponerPaquete(Ingrediente ing)
	{		
		if(ingrediente != null)
			return;
		
		ingrediente = ing;

		BaseDeDatos bd = new BaseDeDatos();						
		
		bd.ejectuarDML(" update estante set " + 
				         " ingrediente = "  + NoSQLInjection.comillas(ing.clave) + " , " + 
				         " cantidad = " + ing.cantidadPorPaquete + ", " + 
				         " fechaColocado = now()" +
				         " where posicionX = " + posicionX + 
				         " and posicionY = " +  posicionY + 
				         " and altura = " + altura);
	}
	
	
	public int obtenerLugaresDisponibles(){
		int disponibles = 0;
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select "+ingrediente.cantidadPorPaquete+"-cantidad from estante"+
											"where altura = " + altura+
											"and posicionX = "+ posicionX + 
											"and posicionY = "+ posicionY);
			
			rs.first();
			disponibles = rs.getInt(1);				
			
			bd.desconectar();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return disponibles;
	}
	
	public static Estante apartarEstanteLibre(int altura)
	{
		return apartar(altura,0);
	}
	
	public static Estante apartarRefriLibre(int altura)
	{
		return apartar(altura,1);
	}
	
	public static Estante apartar(int altura,int refri)
	{
		Estante est = null;
		
		try
		{
			BaseDeDatos bd = new BaseDeDatos();
			
			bd.conectar();
			
			ResultSet rs = bd.realizarQuery(" select * from estante where altura <= " + altura+
											" and isnull(ingrediente) and refrigerador="+refri+
											" order by altura desc");
			
			if(rs.first()){
				est = new Estante();					
				
				est.posicionX = rs.getInt("posicionX");
				est.posicionY = rs.getInt("posicionY");
				est.altura = rs.getInt("altura");
				est.refrigerador = rs.getBoolean("refrigerador");
				est.ingrediente = null;
						
				//Marcar estante como ocupado
				bd.ejectuarDML(" update estante set ingrediente = temp , " + 
				         " where posicionX = " + est.posicionX + 
				         " and posicionY = " +  est.posicionY + 
				         " and altura = " + est.altura);
			}else{
				return null;
			}
			bd.desconectar();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return est;
	}
	
	public static void liberarEstante(int x, int y, int altura)
	{
		BaseDeDatos bd = new BaseDeDatos();
		bd.ejectuarDML(" update estante set ingrediente = null , " + 
			         " where posicionX = " + x + 
			         " and posicionY = " +  y + 
			         " and altura = " + altura);

	}
	
	public static void vaciaTodo(){
		BaseDeDatos bd = new BaseDeDatos();
		bd.ejectuarDML(" update estante set ingrediente = null, fechaColocado=null, cantidad=0");
	}
	@Override
	public String toString() {
		String s = 
			  "posicionX = " + posicionX +
			"\nposicionY = " + posicionY +
			"\naltura = " + altura +
			"\nrefrigerador = " + refrigerador +
			"\ningrediente = " + (ingrediente == null ? null : ingrediente.nombre +
			"\nfecha colocado = " + fechaColocado +
			"\ncantidad = " + cantidad);			
		return s;
	}

	public int obtenerCarrilCercano(AgenteBodego.Tipo tipo){
		if(!refrigerador)
			return posicionX+tipo.ordinal()+2;
		else
			return posicionX+tipo.ordinal()-4;
	}
}
