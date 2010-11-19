package comportamientos;

import sql.Estante;
import agentes.AgenteBodego;
import jade.core.behaviours.TickerBehaviour;

public class Llevar extends TickerBehaviour{

	private static final long serialVersionUID = 7145538917348447874L;
	private static final long tiempoDeMovimiento = 1000L;
	AgenteBodego miAgente;
	
	private Estante estante= null;
	private int metaX = 0;
	private int metaY = 0;
	private int cantidadRecogida = 0;
	
	public Llevar(AgenteBodego a) {
		super(a, tiempoDeMovimiento);
		miAgente=a;
	}
	
	@Override
	protected void onTick() {
		if(miAgente.ingredientePorLlevar != null){
			if(estante==null && cantidadRecogida < miAgente.ingredientePorLlevar.getCantidad()){
				estante = Estante.obtenerEstante(miAgente.ingredientePorLlevar.getIngrediente(),miAgente.altura);
				asignaMetas();
			}
			if(estante!=null){
				ir();
			}
			if(cantidadRecogida == miAgente.ingredientePorLlevar.getCantidad()){
				entregar();
			}	
		}else{
			regresar();
		}
	}
	
	private void asignaMetas(){
		metaY = estante.posicionY;
		if(estante.refrigerador)
			metaX = estante.posicionX-1;
		else
			metaX = estante.posicionX+1;
	}
	
	private boolean banderita = true;
	private void ir(){
		if(miAgente.obtenerCarrilCercano()!= estante.obtenerCarrilCercano(miAgente.tipo)){
			if(moverseACarrilCentral()) return;			
			moverseACarrilEstante(); 
			return;
		}
		if(banderita && miAgente.obtenerCarrilCercano()!= miAgente.getX()){
			moverseACarrilEstante();
			return;
		}
		if(miAgente.getY()!=metaY){
			acercarseAMetaY();
			return;
		}
		if(miAgente.getX()!=metaX){
			acercarseAMetaX();
			return;
		}
		if(estante.cantidad > miAgente.ingredientePorLlevar.getCantidad()){
			cantidadRecogida += miAgente.ingredientePorLlevar.getCantidad();
			estante.retirarIngrediente(miAgente.ingredientePorLlevar.getCantidad());
		}else{
			cantidadRecogida += estante.cantidad;
			estante.retirarIngrediente(estante.cantidad);
		}
		estante = null;
		miAgente.cargando = miAgente.ingredientePorLlevar.getIngrediente().clave;
		banderita=true;
	}
	
	private void entregar(){
		if(metaX != 18){
			metaX = 18;
			metaY = 5 + miAgente.tipo.ordinal();
		}
		if(moverseACarrilCentral())return;
		if(miAgente.getX()!=metaX){
			acercarseAMetaX();
			return;
		}
		miAgente.cargando="";
		miAgente.ingredientePorLlevar=null;
		//Dibujar en zona de entrega
	}
	
	private void regresar(){
		if(metaX != 2){
			metaX = 2;
			metaY = 5 + miAgente.tipo.ordinal();
		}
		if(miAgente.getX()!=metaX){
			acercarseAMetaX();
		}else{
			miAgente.job = null;
			this.stop();
		}
	}
	
	private boolean moverseACarrilCentral(){
		if(miAgente.getY() < 5 + miAgente.tipo.ordinal()){
			if(miAgente.obtenerCarrilCercano()>miAgente.getX())
				miAgente.setX(miAgente.getX()+1);
			if(miAgente.obtenerCarrilCercano()<miAgente.getX())
				miAgente.setX(miAgente.getX()-1);
			if(miAgente.obtenerCarrilCercano()==miAgente.getX())
				miAgente.setY(miAgente.getY()+1);
			return true;
		}
		return false;
	}
	private void moverseACarrilEstante(){
		if(estante.obtenerCarrilCercano(miAgente.tipo) > miAgente.getX())
			miAgente.setX(miAgente.getX()+1);
		else
			miAgente.setX(miAgente.getX()-1);
		if(miAgente.getX()==estante.obtenerCarrilCercano(miAgente.tipo)){ banderita=false;}
	}
	private void acercarseAMetaY(){
		if(miAgente.getY()>metaY)
			miAgente.setY(miAgente.getY()-1);
		else
			miAgente.setY(miAgente.getY()+1);
	}
	private void acercarseAMetaX(){
		if(miAgente.getX()>metaX)
			miAgente.setX(miAgente.getX()-1);
		else
			miAgente.setX(miAgente.getX()+1);
	}
		
}
