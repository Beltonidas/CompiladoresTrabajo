package Compilador.AccionesSemanticas;

public class AddCaracterToken extends AccionSemantica {

	public AddCaracterToken() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int ejecutar(Character caracter) {
		if (AccionSemantica.modoComentario) {
			return 1;
		}
		AccionSemantica.tokenActual.appendLexema(caracter);
		return 1;
	}
}
