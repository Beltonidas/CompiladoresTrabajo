package Compilador.AccionesSemanticas;

import Compilador.AnalizadorLexico;

public class VerificarRangoEntero extends AccionSemantica {

	public VerificarRangoEntero() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		Integer valor = Integer.parseInt(AccionSemantica.tokenActual.getLexema().toString());
		AccionSemantica.tokenActual.setId(258);
		AccionSemantica.tokenActual.setTipo("ui8");
		if (valor>255) {
			AnalizadorLexico.paruser.warningEnXY("Constante entera fuera de rango. Rango Permitido 0 <= x <= 255. Seteada a 255");
			AccionSemantica.tokenActual.setLexema("255");
		}
		return 0;
	}

}
