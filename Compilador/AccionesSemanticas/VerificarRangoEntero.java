package Compilador.AccionesSemanticas;

import Compilador.TokenLexema;

public class VerificarRangoEntero extends AccionSemantica {

	public VerificarRangoEntero() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		Integer valor = Integer.parseInt(AccionSemantica.tokenActual.getLexema().toString());
		if (valor<256 && valor>-1) {
			//se pudo por defecto
		    System.out.println(AccionSemantica.tokenActual.getLexema().toString());
			AccionSemantica.tokenActual.setId("Constante Entera");
			return 0;
		}else {
			AccionSemantica.tokenActual = new TokenLexema("Constante entera fuera de rango. Rango Permitido 0 <= x <= 255");
			return -1;
		}
	}

}
