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
		recetas = Receta.obtenerRecetasSaludables();
		procesaRecetas();		
	}

}
