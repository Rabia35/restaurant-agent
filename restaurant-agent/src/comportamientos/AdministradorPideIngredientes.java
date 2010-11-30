package comportamientos;

import sql.Pedido;
import sql.Receta;
import agentes.AgenteAdministrador;
import jade.core.behaviours.TickerBehaviour;

public class AdministradorPideIngredientes extends TickerBehaviour 
{
	private static final long serialVersionUID = -5049217969678301145L;
	private static final long TIEMPO_REVISION = 10000;	
	
	public AgenteAdministrador agente;
	
	public AdministradorPideIngredientes(AgenteAdministrador a) 
	{
		super(a, TIEMPO_REVISION);
		agente = a;
	}

	@Override
	protected void onTick() 
	{
		revisaPedidosIncompletos();		
	}

	private void revisaPedidosIncompletos()
	{
		Pedido[] pedidos = Pedido.obtenerPlatillos();
		
		if (pedidos == null)
			return;
		
		for (int i = 0; i < pedidos.length; i++)
			if (pedidos[i].valorReceta < Receta.VALOR_RECETA_COMPLETA)
				pedidos[i].receta.resurtirReceta();
	}
}
