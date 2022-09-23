package Compilador.AccionesSemanticas;

public class FinComentario extends AccionSemantica {

	public FinComentario() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.modoComentario=false;
		return 1;
	}
}
