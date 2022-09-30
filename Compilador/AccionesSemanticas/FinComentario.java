package Compilador.AccionesSemanticas;

public class FinComentario extends AccionSemantica {

	public FinComentario() {
	}

	
	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.setId("Comentario");
		return 1;
	}
}
