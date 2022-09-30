package Compilador.AccionesSemanticas;

import Compilador.TokenLexema;

public class VerificarRangoEntero extends AccionSemantica {

	public VerificarRangoEntero() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		Integer valor = Integer.parseInt(AccionSemantica.tokenActual.getLexema().toString());
		if (valor<256 && valor>0) {
			//se pudo por defecto
			AccionSemantica.tokenActual.setId("Entero");
			return 0;
		}else {
			AccionSemantica.tokenActual = new TokenLexema("Constante entera fuera de rango. Rango Permitido 0 <= x <= 255");
			return -1;
		}
	}

}
