package Compilador.AccionesSemanticas;

import Compilador.TokenLexema;

public class VerificarRangoDouble extends AccionSemantica {

	public VerificarRangoDouble() {
	}

	@Override
	public int ejecutar(Character caracter) {
		Double parteDecimal=0.0;
		int parteExponente=0;
		StringBuilder lexema = AccionSemantica.tokenActual.getLexema();
		int indexPunto = lexema.indexOf(".");
		int indexExponente = lexema.indexOf("D");
		
		
		if (indexPunto==0) {
			//Caso en que no hay parte entera
			if (indexExponente!=-1) {
				//Caso en que hay parte exponencial
				parteExponente = Integer.parseInt(lexema.substring(indexExponente+1,lexema.length()));
				if (parteExponente>=308||parteExponente<=-308) {
					AccionSemantica.tokenActual = new TokenLexema(,"Exponente de la constante double fuera de rango.");
					return -1;
				}
				parteDecimal = Double.parseDouble("0"+lexema.substring(indexPunto, indexExponente)+"E"+lexema.substring(indexExponente+1,lexema.length()));
			}else {
				parteDecimal = Double.parseDouble("0"+lexema.substring(indexPunto, lexema.length()));
			}
			if (Double.MIN_VALUE > parteDecimal || parteDecimal > Double.MAX_VALUE) {
				AccionSemantica.tokenActual = new TokenLexema(,"Valor de la constante double fuera de rango.");
				return -1;
			}
		} else {
			//Caso en que hay parte entera
			if (indexExponente!=-1) {
				//Caso en que hay parte exponencial
				parteExponente = Integer.parseInt(lexema.substring(indexExponente+1,lexema.length()));
				if (parteExponente>=308||parteExponente<=-308) {
					AccionSemantica.tokenActual = new TokenLexema(,"Exponente de la constante double fuera de rango.");
					return -1;
				}
				parteDecimal = Double.parseDouble(lexema.substring(0, indexExponente)+"0E"+lexema.substring(indexExponente+1,lexema.length()));
			} else {
				parteDecimal = Double.parseDouble(lexema.substring(0, lexema.length())+"0");
			}
			if (Double.MIN_VALUE > parteDecimal || parteDecimal > Double.MAX_VALUE) {
				AccionSemantica.tokenActual = new TokenLexema(,"Valor de la constante double fuera de rango.");
				return -1;
			}
		}
		return 0;
	}
}
