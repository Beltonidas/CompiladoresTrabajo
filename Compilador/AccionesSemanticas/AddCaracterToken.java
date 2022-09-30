package Compilador.AccionesSemanticas;

public class AddCaracterToken extends AccionSemantica {

	public AddCaracterToken() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.appendLexema(caracter);
		return 1;
	}
}
