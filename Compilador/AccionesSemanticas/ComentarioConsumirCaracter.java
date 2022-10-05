package Compilador.AccionesSemanticas;

public class ComentarioConsumirCaracter extends AccionSemantica {

	public ComentarioConsumirCaracter() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		return 1;
	}

}
