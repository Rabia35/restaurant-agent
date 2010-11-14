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
		if(ingredienteRecogido){
			if(miAgente.ingredientePorAlmacenar!=null){
				asignaMetas();
				ir();
			}else{
				regresar();
			}
		}else{
			if(estoyEnZonaPaquetes()){
				ingredienteRecogido = true;
				miAgente.cargando = miAgente.ingredientePorAlmacenar.clave;
				miAgente.actualizarArchivoDeAgente();
			}else{
				regresar();//tengo otro estante
			}
		}
	}
	
	private void asignaMetas(){
		if(metaY==estante.posicionY)return;
		metaY = estante.posicionY;
		if(estante.refrigerador)
			metaX = estante.posicionX-1;
		else
			metaX = estante.posicionX+1;
	}
	private boolean estoyEnZonaPaquetes(){
		return miAgente.getX()==2 && miAgente.getY()==5 + miAgente.tipo.ordinal();
	}
	
	private void ir(){
		if(subirACarrilEstante()) return;
		if(acercarseAMetaY())return;
		if(acercarseAMetaX())return;
		estante.ponerPaquete(miAgente.ingredientePorAlmacenar);
		miAgente.ingredientePorAlmacenar=null;
		miAgente.cargando = "";
	}
	
	private void regresar(){
		if(metaX != 2){
			metaX = 2;
			metaY = 5 + miAgente.tipo.ordinal();
		}
		if(moverseACarrilEstante())return;
		if(acercarseAMetaY())return;
		if(acercarseAMetaX())return;
		if(ingredienteRecogido){
			miAgente.job = null;
			this.stop();
		}
	}
	
	
	private boolean subirACarrilEstante(){
		if(miAgente.getY()!=metaY && miAgente.getX() < estante.obtenerCarrilCercano(miAgente.tipo)){
			miAgente.setX(miAgente.getX()+1);
			return true;
		}
		return false;
	}
	
	private boolean moverseACarrilEstante(){
		if(miAgente.getY() == metaY || miAgente.obtenerCarrilCercano() == miAgente.getX())
			return false;
		if(miAgente.getY()!=metaY && miAgente.obtenerCarrilCercano() > miAgente.getX())
			miAgente.setX(miAgente.getX()+1);
		if(miAgente.getY()!=metaY && miAgente.obtenerCarrilCercano() < miAgente.getX())
			miAgente.setX(miAgente.getX()-1);
		return true;
	}
	
	private boolean acercarseAMetaX(){
		if(miAgente.getX()!=metaX){
			if(miAgente.getX()>metaX)
				miAgente.setX(miAgente.getX()-1);
			else
				miAgente.setX(miAgente.getX()+1);
			return true;
			}
		return false;
	}
	
	private boolean acercarseAMetaY(){
		if(miAgente.getY()!=metaY){
			if(miAgente.getY()>metaY)
				miAgente.setY(miAgente.getY()-1);
			else
				miAgente.setY(miAgente.getY()+1);
			return true;
		}
		return false;
	}
	
}
