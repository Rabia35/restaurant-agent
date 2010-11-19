package agentes;

import sql.Receta;
import util.TipoMenu;

public class AgenteMenuCodicioso extends AgenteMenu
{
	private static final long serialVersionUID = -5823740765821676839L;

	public void setup() 
	{		
		tipo = TipoMenu.MenuCodicioso;
		super.setup();
	}
	
	@Override
	public void escogeRecetas() 
	{	
		String[][] recetas = Receta.obtenerRecetasCaras();
		String rFinal = "";
		int t = 0;
		
		cargaMenu();
		
		for (int i = 0; i < recetas.length; i++)
			if(!estaEnMenu(recetas[i][0]))
			{
				rFinal += recetas[i][0] + "#" + recetas[i][1] + "#";
				t++;
				if (t == PROPUESTAS_POR_MENU)
					break;
			}		
		
		negociaMenu(rFinal);
	}

}
