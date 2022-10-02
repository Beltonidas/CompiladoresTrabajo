package Compilador.AccionesSemanticas;

public class FinComentario extends AccionSemantica {

	public FinComentario() {
	}

	
	@Override
	public int ejecutar(Character caracter) {
		return 1;
	}
}
