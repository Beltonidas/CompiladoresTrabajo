package Compilador.AccionesSemanticas;

import Compilador.AnalizadorLexico;

public class VerificarCadenaCaracteres extends AccionSemantica {

	public VerificarCadenaCaracteres(){
	}

	@Override
	public int ejecutar(Character caracter) {
		if (caracter=='\n'){
            AnalizadorLexico.paruser.errorEnXY("Salto de linea en cadena de caracteres no valida");
            AccionSemantica.tokenActual.setId(259);
            return -1;
		} else {
		    AccionSemantica.tokenActual.appendLexema(caracter);
			AccionSemantica.tokenActual.setId(259);
			return 1;
		}
    }

}
