package Compilador.AccionesSemanticas;

public class ComienzoComentario extends AccionSemantica {

	public ComienzoComentario() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		return 1;
	}

}
