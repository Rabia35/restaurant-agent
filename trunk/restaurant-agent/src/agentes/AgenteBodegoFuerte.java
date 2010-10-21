package agentes;

public class AgenteBodegoFuerte extends AgenteBodego {

	private static final long serialVersionUID = -3231415096877809818L;
	
	protected void setup(){
		fuerza = 15000;
		altura = 1;
		setup(Tipo.Fuerte);
	}

}
