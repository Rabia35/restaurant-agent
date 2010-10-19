package util;

public class NoSQLInjection 
{
	public static String comillas(String texto)
	{
		return "'" + texto.replace("'", "''") + "'";
	}
}