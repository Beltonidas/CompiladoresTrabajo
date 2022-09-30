package Compilador.AccionesSemanticas;

import Compilador.TokenLexema;

public class GenerarNuevoToken extends AccionSemantica {

	public GenerarNuevoToken() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual = new TokenLexema(caracter.toString());
		return 1;
	}

}
