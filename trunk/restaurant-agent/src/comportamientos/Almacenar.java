package comportamientos;

import sql.Estante;
import agentes.AgenteBodego;
import jade.core.behaviours.TickerBehaviour;

public class Almacenar extends TickerBehaviour {

	private static final long serialVersionUID = -3170841491165005518L;
	private static final long tiempoDeMovimiento = 1000L;
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
		if(subir()) return;
		if(izquierda())return;
		if(bajar()) return;
		estante.ponerIngrediente(miAgente.ingredientePorAlmacenar);
		miAgente.ingredientePorAlmacenar=null;
	}
	
	private void regresar(){
		if(subir()) return;
		if(derecha())return;
		if(bajar()) return;
		if(ingredienteRecogido){
			this.stop();
		}
	}
	
	private boolean estoyEnZonaPaquetes(){
		return miAgente.getX()==2 && miAgente.getY()==5 + miAgente.tipo.ordinal();
	}
	
	private boolean subir(){
		if(miAgente.getY()!=metaY && miAgente.getX()<metaX+miAgente.tipo.ordinal()+1){
			miAgente.setX(miAgente.getX()+1);
			return true;
		}	
		return false;
	}
	private boolean izquierda(){
		if(miAgente.getY()>metaY){
			miAgente.setY(miAgente.getY()-1);
			return true;
		}
		return false;
	}
	private boolean bajar(){
		if(miAgente.getX()>metaX){
			miAgente.setX(miAgente.getX()-1);
			return true;
		}
		return false;
	}
	private boolean derecha(){
		if(miAgente.getY()<metaY){
			miAgente.setY(miAgente.getY()+1);
			return true;
		}
		return false;
	}
}
