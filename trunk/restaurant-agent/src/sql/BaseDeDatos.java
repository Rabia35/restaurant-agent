package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDatos 
{	
	private String nombreUsuario = "restaurantagent";
	private String contraseña = "restaurantagent";
	private String URL = "jdbc:mysql://localhost:3306/restaurantagent";
	
	private Connection conn;
	private Statement stat; 

	public void conectar()
	{
		try 
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			conn = DriverManager.getConnection (URL, nombreUsuario, contraseña);
			stat = conn.createStatement();		
		} catch (Exception e) 
		{
			System.err.println(e);
		}				
	}
	
	public ResultSet realizarQuery(String query)
	{
		ResultSet resultado = null;
		try 
		{
			resultado = stat.executeQuery(query);												
		}catch (Exception e) 
		{	
			System.err.println(e);
		}		
		return resultado;
	}
	
	public void desconectar()
	{
		try 
		{
			conn.close();
		} catch (SQLException e) 
		{
			System.err.println(e);
		}
	}

	public void ejectuarDML(String query)
	{			
		try {
			conectar();
			stat.execute(query);
			desconectar();	
		} catch (Exception e) {
			//NOTA: Esta operación puede dar excepción seguido
			//por lo que no se imprime nada
		}				
	}
}
