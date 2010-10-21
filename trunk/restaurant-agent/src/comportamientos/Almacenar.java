package comportamientos;

import sql.Estante;
import agentes.AgenteBodego;
import jade.core.behaviours.TickerBehaviour;

public class Almacenar extends TickerBehaviour {

	private static final long serialVersionUID = -3170841491165005518L;
	private static final long tiempoDeMovimiento = 500L;
	AgenteBodego miAgente;
	
	private int metaX = 0;
	private int metaY = 0;
	private Estante estante= null;
	
	private boolean ingredienteRecogido=false;
	
	public Almacenar(AgenteBodego a, Estante estante) {
		super(a, tiempoDeMovimiento);
		miAgente=a;
		this.estante=estante;
	}
	
	@Override
	protected void onTick() {
		if(estoyEnZonaPaquetes()){
			ingredienteRecogido = true;
		}
		if(ingredienteRecogido && miAgente.ingredientePorAlmacenar!=null){
			if(metaX==0){
				metaX = estante.posicionX+1;
				metaY = estante.posicionY;
			}
			ir();
		}else{
			if(metaX != 2){
				metaX = 2;
				metaY = 5 + miAgente.tipo.ordinal();
			}
			regresar();
		}
	}
	
	private void ir(){
		if(miAgente.y!=metaY && miAgente.x<metaX+miAgente.tipo.ordinal()+1){
			miAgente.x++;
			//escribir a archivo
			return;
		}
		if(miAgente.y>metaY){
			miAgente.y--;
			//escribir a archivo
			return;
		}
		if(miAgente.x>metaX){
			miAgente.x--;
			//escribir a archivo			
			return;
		}
		//escribir a archivo
		estante.ponerIngrediente(miAgente.ingredientePorAlmacenar);
		miAgente.ingredientePorAlmacenar=null;
	}
	
	private void regresar(){
		if(miAgente.y!=metaY && miAgente.x<metaX+miAgente.tipo.ordinal()+1){
			miAgente.x++;
			return;
		}
		if(miAgente.y<metaY){
			miAgente.y++;
			return;
		}
		if(miAgente.x>metaX){
			miAgente.x--;
			return;
		}
		if(ingredienteRecogido){
			this.stop();
		}
	}
	
	private boolean estoyEnZonaPaquetes(){
		return miAgente.x==2 && miAgente.y==5 + miAgente.tipo.ordinal();
	}
}
