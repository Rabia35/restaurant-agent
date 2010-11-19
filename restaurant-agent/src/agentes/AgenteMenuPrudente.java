package agentes;

import sql.Receta;
import util.TipoMenu;

public class AgenteMenuPrudente extends AgenteMenu
{
	private static final long serialVersionUID = -7781407676846327559L;
	
	public void setup() 
	{		
		tipo = TipoMenu.MenuPrudente;
		super.setup();
	}

	@Override
	public void escogeRecetas() 
	{	
		recetas = Receta.obtenerRecetasPrudentes();
		procesaRecetas();
	}

}
