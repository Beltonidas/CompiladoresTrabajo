package Compilador.AccionesSemanticas;

import Compilador.AnalizadorLexico;

public class VerificarRangoDouble extends AccionSemantica {

	public VerificarRangoDouble() {
	}

	@Override
	public int ejecutar(Character caracter) {
		StringBuilder lexema = AccionSemantica.tokenActual.getLexema();
		int indexExponente = lexema.indexOf("D");
		if (indexExponente!=-1) {
	        lexema.deleteCharAt(indexExponente);
	        lexema.insert(indexExponente, 'E');
	    }
		AccionSemantica.tokenActual.setId(258);
		Double parteDecimal=Double.parseDouble(lexema.toString());
	    if (parteDecimal==0.0 ||(Double.parseDouble("2.2250738585072014E-308") < parteDecimal && parteDecimal < Double.parseDouble("1.7976931348623157E308"))) {
	        AccionSemantica.tokenActual.setLexema(parteDecimal.toString());
	        return 0;
	    }
	    AccionSemantica.tokenActual.setLexema("0.0");
        AnalizadorLexico.paruser.warningEnXY("Valor de la constante fuera de rango seteada a 0.0");
		return 0;
	}
}
