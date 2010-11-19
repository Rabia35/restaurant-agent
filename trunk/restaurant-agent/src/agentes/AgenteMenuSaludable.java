package agentes;

import sql.Receta;
import util.TipoMenu;

public class AgenteMenuSaludable extends AgenteMenu
{
	private static final long serialVersionUID = 2427010345261597178L;

	public void setup() 
	{		
		tipo = TipoMenu.MenuSaludable;
		super.setup();
	}
	
	@Override
	public void escogeRecetas() 
	{
		String[][] recetas = Receta.obtenerRecetasSaludables();
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
