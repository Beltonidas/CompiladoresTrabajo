package Compilador.AccionesSemanticas;

public class ComienzoComentario extends AccionSemantica {

	public ComienzoComentario() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.resetLexema();
		return 1;
	}

}
