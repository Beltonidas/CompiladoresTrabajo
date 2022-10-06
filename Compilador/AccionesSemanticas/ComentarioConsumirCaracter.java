package Compilador.AccionesSemanticas;

import Compilador.AnalizadorLexico;

public class ComentarioConsumirCaracter extends AccionSemantica {

	public ComentarioConsumirCaracter() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.setLexema("");
		return 1;
	}

}
