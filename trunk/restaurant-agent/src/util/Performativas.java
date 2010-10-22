package util;

public class Performativas 
{
	public static int CONFIRMAR = 0;
	public static int TRAER = 1;
	public static int INFORMAR = 2;
	public static int CAMBIAR = 3;
	public static int SUGERIR = 4;
	
	public static String getNombre(int i){
		switch(i){
		case 0: return "CONFIRMAR";
		case 1: return "TRAER";
		case 2: return "INFORMAR";
		case 3: return "CAMBIAR";
		default: return "SUGERIR";
		}
		 
		
	}
}
