package Compilador.AccionesSemanticas;

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
			if (indexExponente!=-1) {
				parteExponente = Integer.parseInt(lexema.substring(indexExponente+1,lexema.length()));
				if (parteExponente>=308||parteExponente<=-308) {
				    AccionSemantica.getNewToken("Exponente de la constante double fuera de rango.");
	                AccionSemantica.tokenActual.setId(-258);
					return -1;
				}
				parteDecimal = Double.parseDouble("0"+lexema.substring(indexPunto, indexExponente)+"E"+lexema.substring(indexExponente+1,lexema.length()));
			}else {
				parteDecimal = Double.parseDouble("0"+lexema.substring(indexPunto, lexema.length()));
			}
			if (Double.MIN_VALUE > parteDecimal || parteDecimal > Double.MAX_VALUE) {
			    AccionSemantica.getNewToken("Valor de la constante double fuera de rango.");
                AccionSemantica.tokenActual.setId(-258);
				return -1;
			}
		} else {
			if (indexExponente!=-1) {
				parteExponente = Integer.parseInt(lexema.substring(indexExponente+1,lexema.length()));
				if (parteExponente>=308||parteExponente<=-308) {
					AccionSemantica.getNewToken("Exponente de la constante double fuera de rango.");
	                AccionSemantica.tokenActual.setId(-258);
					return -1;
				}
				parteDecimal = Double.parseDouble(lexema.substring(0, indexExponente)+"0E"+lexema.substring(indexExponente+1,lexema.length()));
			} else {
				parteDecimal = Double.parseDouble(lexema.substring(0, lexema.length())+"0");
			}
			if (Double.MIN_VALUE > parteDecimal || parteDecimal > Double.MAX_VALUE) {
			    AccionSemantica.getNewToken("Valor de la constante double fuera de rango.");
                AccionSemantica.tokenActual.setId(-258);
				return -1;
			}
		}
		AccionSemantica.tokenActual.setId(258);
		return 0;
	}
}
